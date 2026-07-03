package com.kuit.youthroulette.data.remote.dto

data class SignUpRequest(
    val loginId: String,
    val nickname: String,
    val password: String
)

data class SignUpResponse(
    val id: Int,
    val loginId: String,
    val nickname: String
)

data class LoginRequest(
    val loginId: String,
    val password: String
)

data class LoginResponse(
    val accessToken: String,
    val tokenType: String
)
