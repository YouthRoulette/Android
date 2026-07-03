package com.kuit.youthroulette.data

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.kuit.youthroulette.data.remote.ApiClient
import com.kuit.youthroulette.data.remote.dto.PresignedUrlRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class ImageRepository {

    private val api = ApiClient.api
    private val okHttpClient = OkHttpClient()

    suspend fun uploadImageToS3(
        context: Context,
        imageUri: Uri
    ): String = withContext(Dispatchers.IO) {
        val contentResolver = context.contentResolver

        val contentType = contentResolver.getType(imageUri) ?: "image/jpeg"
        val fileName = getFileName(context, imageUri) ?: "bucket-proof.jpg"

        val presignedResponse = api.getPresignedUrl(
            PresignedUrlRequest(
                fileName = fileName,
                contentType = contentType
            )
        )

        if (!presignedResponse.isSuccessful) {
            throw IOException("Presigned URL 발급 실패: ${presignedResponse.code()}")
        }

        val presignedBody = presignedResponse.body()
            ?: throw IOException("Presigned URL 응답이 비어 있습니다.")

        val imageBytes = contentResolver.openInputStream(imageUri)?.use {
            it.readBytes()
        } ?: throw IOException("이미지 파일을 읽을 수 없습니다.")

        val requestBody = imageBytes.toRequestBody(contentType.toMediaTypeOrNull())

        val uploadRequest = Request.Builder()
            .url(presignedBody.presignedUrl)
            .put(requestBody)
            .addHeader("Content-Type", contentType)
            .build()

        val uploadResponse = okHttpClient.newCall(uploadRequest).execute()

        if (!uploadResponse.isSuccessful) {
            throw IOException("S3 이미지 업로드 실패: ${uploadResponse.code}")
        }

        presignedBody.imageUrl
    }

    private fun getFileName(context: Context, uri: Uri): String? {
        val cursor = context.contentResolver.query(
            uri,
            null,
            null,
            null,
            null
        )

        cursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (it.moveToFirst() && nameIndex != -1) {
                return it.getString(nameIndex)
            }
        }

        return null
    }
}