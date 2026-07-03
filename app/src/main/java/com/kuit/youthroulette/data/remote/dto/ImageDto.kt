package com.kuit.youthroulette.data.remote.dto

data class PresignedUrlRequest(
    val fileName: String,
    val contentType: String
)

data class PresignedUrlResponse(
    val presignedUrl: String,
    val imageUrl: String
)
