package com.kuit.youthroulette.data.remote.dto

data class FriendRequestCreate(
    val loginId: String
)

data class FriendRequestDto(
    val friendId: Int,
    val requesterId: Int,
    val requesterNickname: String,
    val receiverId: Int,
    val receiverNickname: String,
    val status: String,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
