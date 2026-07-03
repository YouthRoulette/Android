package com.kuit.youthroulette.data.remote.dto

data class FriendDto(
    val friendId: Int,
    val userId: Int,
    val nickname: String,
    val emojiIndex: Int? = null,
    val colorIndex: Int? = null
)
