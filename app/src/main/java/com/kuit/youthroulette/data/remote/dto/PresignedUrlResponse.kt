package com.kuit.youthroulette.data.remote.dto

data class PresignedUrlResponse(
    val uploadUrl: String,
    val imageUrl: String
)