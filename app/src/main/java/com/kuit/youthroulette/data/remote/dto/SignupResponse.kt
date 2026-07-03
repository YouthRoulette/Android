package com.kuit.youthroulette.data.remote.dto
//회원가입 성공 시 서버가 내려주는 응답 body
data class SignupResponse(
    val id: Int,
    val loginId: String,
    val nickname: String
)
