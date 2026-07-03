package com.kuit.youthroulette.model

data class User(
    val userId: Int,
    val loginId: String,
    val nickname: String,
    val emojiIndex: Int,
    val colorIndex: Int,
    val challengedCount: Int,
    val completedCount: Int
)
