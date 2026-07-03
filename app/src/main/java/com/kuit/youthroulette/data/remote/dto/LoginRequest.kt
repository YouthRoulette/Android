package com.kuit.youthroulette.data.remote.dto
//로그인 버튼을 눌렀을 때 서버로 보내는 요청 body
data class LoginRequest(
    val loginId: String,
    val password: String
)
