package com.kuit.youthroulette.data.remote.dto

data class BucketDto(
    val bucketId: Int,
    val title: String,
    val emojiIndex: Int = 0,
    val colorIndex: Int = 0,
    val status: String? = null,
    val createdAt: String? = null,
    val startedAt: String? = null,
    val completedAt: String? = null
)