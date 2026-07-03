package com.kuit.youthroulette.data.remote.dto
//회원가입 버튼을 눌렀을 때 서버로 보내는 요청 body
data class SignupRequest(
    val loginId: String,
    val nickname: String,
    val password: String
)
