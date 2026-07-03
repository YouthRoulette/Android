package com.kuit.youthroulette.data.remote.dto

data class UserDto(
    val userId: Int,
    val loginId: String,
    val nickname: String,
    val emojiIndex: Int,
    val colorIndex: Int,
    val challengedCount: Int,
    val completedCount: Int,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

data class NicknameUpdateRequest(
    val nickname: String
)

data class NicknameUpdateResponse(
    val userId: Int,
    val loginId: String,
    val nickname: String
)

data class ProfileUpdateRequest(
    val emojiIndex: Int,
    val colorIndex: Int
)

data class ProfileUpdateResponse(
    val userId: Int,
    val emojiIndex: Int,
    val colorIndex: Int
)
