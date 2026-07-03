package com.kuit.youthroulette.data

import com.kuit.youthroulette.data.remote.ApiClient
import com.kuit.youthroulette.data.remote.dto.LoginRequest
import com.kuit.youthroulette.data.remote.dto.SignUpRequest

object AuthRepository {

    private val api = ApiClient.api

    suspend fun signUp(loginId: String, nickname: String, password: String): Result<Unit> =
        runCatching {
            val response = api.signUp(SignUpRequest(loginId, nickname, password))
            SessionManager.updateAuth(null, response.loginId, response.nickname)
        }

    suspend fun login(loginId: String, password: String): Result<Unit> =
        runCatching {
            val response = api.login(LoginRequest(loginId, password))
            SessionManager.updateAuth(response.accessToken, loginId, null)
        }
}
