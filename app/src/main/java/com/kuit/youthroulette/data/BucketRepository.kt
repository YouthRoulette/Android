package com.kuit.youthroulette.data

import androidx.compose.runtime.mutableStateListOf
import com.kuit.youthroulette.data.mapper.toBucketItem
import com.kuit.youthroulette.data.remote.ApiClient
import com.kuit.youthroulette.data.remote.dto.BucketDto
import com.kuit.youthroulette.data.remote.dto.CreateBucketRequest
import com.kuit.youthroulette.data.remote.toApiException
import com.kuit.youthroulette.model.BucketItem
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException

object BucketRepository {

    val bucketItems = mutableStateListOf<BucketItem>()

    // 서버에서 버킷 목록을 불러와 로컬 목록을 교체함
    suspend fun fetchBuckets(): Result<Unit> {
        return try {
            val response = ApiClient.api.getBuckets()
            bucketItems.clear()
            bucketItems.addAll(response.map { it.toBucketItem() })
            Result.success(Unit)
        } catch (e: HttpException) {
            Result.failure(e.toApiException())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 서버에 버킷리스트 생성 요청 후 응답으로 받은 버킷을 목록에 추가
    // 4xx/5xx 응답은 ApiException(errorResponse)으로, 그 외(네트워크 단절 등)는 원본 예외로 감싸서 반환
    suspend fun createBucket(title: String, emojiIndex: Int, colorIndex: Int): Result<BucketItem> {
        return try {
            val response = ApiClient.api.createBucket(
                CreateBucketRequest(title = title, emojiIndex = emojiIndex, colorIndex = colorIndex)
            )
            Result.success(response.toBucketItem().also { bucketItems.add(it) })
        } catch (e: HttpException) {
            Result.failure(e.toApiException())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 서버에 버킷 삭제 요청 후 성공하면 로컬 목록에서도 제거
    // 응답 바디를 Gson으로 파싱하지 않고 HTTP 상태코드만으로 성공 여부를 판단함
    // (삭제 응답은 바디가 없거나(204) 형식이 달라 파싱 실패로 오탐될 수 있었음)
    suspend fun deleteBucket(id: Int): Result<Unit> {
        return try {
            val response = ApiClient.api.deleteBucket(id)
            if (response.isSuccessful) {
                bucketItems.removeAll { it.id == id }
                Result.success(Unit)
            } else {
                Result.failure(response.toApiException())
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 서버가 미완료 버킷 중 하나를 골라 반환함 (도전 시작 처리는 별도 API로 진행)
    // 404 NO_BUCKET_ITEMS(도전 가능한 버킷 없음), 409 ALREADY_IN_PROGRESS(이미 도전 중) 등은 ApiException으로 전달됨
    suspend fun spinRoulette(): Result<BucketItem> {
        return try {
            val response = ApiClient.api.spinRoulette()
            Result.success(response.toBucketItem())
        } catch (e: HttpException) {
            Result.failure(e.toApiException())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 서버에 도전 시작 요청 후 응답으로 받은 최신 버킷 상태를 로컬 목록에 반영
    // 404 BUCKET_NOT_FOUND, 409 BUCKET_ALREADY_STARTED 등은 ApiException으로 전달됨
    suspend fun startChallenge(id: Int): Result<BucketItem> {
        return updateBucketFromServer(id, ApiClient.api::startBucket)
    }

    // 서버에 버킷 미완료 처리 요청 후 응답으로 받은 최신 버킷 상태를 로컬 목록에 반영
    suspend fun incompleteBucket(id: Int): Result<BucketItem> {
        return updateBucketFromServer(id, ApiClient.api::incompleteBucket)
    }

    // 서버에 버킷 완료 처리 요청 후 응답으로 받은 최신 버킷 상태를 로컬 목록에 반영
    // 409 BUCKET_ALREADY_VERIFIED 등은 ApiException으로 전달됨
    suspend fun complete(id: Int): Result<BucketItem> {
        return updateBucketFromServer(id, ApiClient.api::completeBucket)
    }

    private suspend fun updateBucketFromServer(
        id: Int,
        request: suspend (Int) -> BucketDto
    ): Result<BucketItem> {
        return try {
            val updated = request(id).toBucketItem()
            val index = bucketItems.indexOfFirst { it.id == id }
            if (index != -1) {
                bucketItems[index] = updated
            }
            Result.success(updated)
        } catch (e: HttpException) {
            Result.failure(e.toApiException())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
