package com.kuit.youthroulette.data.remote.dto
//로그인 성공 시 서버가 내려주는 응답 body
data class LoginResponse(
    val accessToken: String,
    val tokenType: String
)
