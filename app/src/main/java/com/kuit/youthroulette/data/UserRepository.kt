package com.kuit.youthroulette.data

import com.kuit.youthroulette.data.local.TokenStore
import com.kuit.youthroulette.data.remote.ApiClient
import com.kuit.youthroulette.data.remote.dto.LoginRequest
import com.kuit.youthroulette.data.remote.dto.SignupRequest
import com.kuit.youthroulette.data.remote.toApiException
import com.kuit.youthroulette.model.User
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.HttpException

object UserRepository {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    // 회원가입 성공 시 로컬 유저 상태도 갱신 (응답에 토큰은 없어서 로그인은 별도로 진행해야 함)
    suspend fun signup(loginId: String, nickname: String, password: String): Result<Unit> {
        return try {
            val response = ApiClient.api.signup(
                SignupRequest(loginId = loginId, nickname = nickname, password = password)
            )
            saveUser(id = response.loginId, nickname = response.nickname)
            Result.success(Unit)
        } catch (e: HttpException) {
            Result.failure(e.toApiException())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 로그인 성공 시 accessToken을 저장해서 이후 요청에 자동으로 실리게 함
    suspend fun login(loginId: String, password: String): Result<Unit> {
        return try {
            val response = ApiClient.api.login(LoginRequest(loginId = loginId, password = password))
            TokenStore.accessToken = response.accessToken
            saveUser(id = loginId)
            Result.success(Unit)
        } catch (e: HttpException) {
            Result.failure(e.toApiException())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun saveUser(
        id: String,
        nickname: String = id,
        profileImageUrl: String? = null
    ) {
        _currentUser.value = User(
            id = id,
            nickname = nickname,
            profileImageUrl = profileImageUrl
        )
    }

    fun getCurrentUser(): User? {
        return _currentUser.value
    }
}