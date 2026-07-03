package com.kuit.youthroulette.data

import com.google.gson.Gson
import com.kuit.youthroulette.data.remote.ApiClient
import com.kuit.youthroulette.data.remote.dto.ErrorResponse
import com.kuit.youthroulette.data.remote.dto.LoginRequest
import com.kuit.youthroulette.data.remote.dto.SignUpRequest
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException

object AuthRepository {

    private val api get() = ApiClient.api
    private val gson = Gson()

    suspend fun signUp(loginId: String, nickname: String, password: String): Result<Unit> {
        return try {
            val response = api.signUp(SignUpRequest(loginId, nickname, password))
            SessionManager.updateAuth(null, response.loginId, response.nickname)
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(Exception(errorMessage(e, "회원가입에 실패했어요. 잠시 후 다시 시도해주세요.")))
        }
    }

    suspend fun login(loginId: String, password: String): Result<Unit> {
        return try {
            val response = api.login(LoginRequest(loginId, password))
            SessionManager.updateAuth(response.accessToken, loginId, null)
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(Exception(errorMessage(e, "로그인에 실패했어요. 잠시 후 다시 시도해주세요.")))
        }
    }

    private fun errorMessage(e: Throwable, fallback: String): String {
        return when (e) {
            is HttpException -> parseServerMessage(e) ?: fallback
            is IOException -> "서버에 연결할 수 없어요. 네트워크 상태를 확인해주세요."
            else -> fallback
        }
    }

    private fun parseServerMessage(e: HttpException): String? {
        val raw = runCatching { e.response()?.errorBody()?.string() }.getOrNull()
        if (raw.isNullOrBlank()) return null
        val error = runCatching { gson.fromJson(raw, ErrorResponse::class.java) }.getOrNull()
            ?: return null
        val fieldReasons = error.errors
            ?.mapNotNull { it.reason?.takeIf { reason -> reason.isNotBlank() } }
            ?.takeIf { it.isNotEmpty() }
        if (fieldReasons != null) return fieldReasons.joinToString("\n")
        return error.message?.takeIf { it.isNotBlank() }
    }
}
