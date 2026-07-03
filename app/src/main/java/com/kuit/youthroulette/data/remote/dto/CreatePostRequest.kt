package com.kuit.youthroulette.data.remote.dto

data class CreatePostRequest(
    val imageUrl: String,
    val reviewText: String? = null,
    val visibility: String,
    val friendIds: List<Long> = emptyList()
)
