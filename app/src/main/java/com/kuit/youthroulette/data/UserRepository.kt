package com.kuit.youthroulette.data

import com.kuit.youthroulette.data.mapper.toUser
import com.kuit.youthroulette.data.remote.ApiClient
import com.kuit.youthroulette.data.remote.dto.NicknameUpdateRequest
import com.kuit.youthroulette.data.remote.dto.ProfileUpdateRequest
import com.kuit.youthroulette.model.User

object UserRepository {

    private val api get() = ApiClient.api

    suspend fun getMyInfo(): Result<User> = runCatching {
        api.getMyInfo().toUser()
    }

    suspend fun updateNickname(nickname: String): Result<String> = runCatching {
        api.updateNickname(NicknameUpdateRequest(nickname)).nickname
    }

    suspend fun updateProfile(emojiIndex: Int, colorIndex: Int): Result<Unit> = runCatching {
        api.updateProfile(ProfileUpdateRequest(emojiIndex, colorIndex))
        Unit
    }
}
