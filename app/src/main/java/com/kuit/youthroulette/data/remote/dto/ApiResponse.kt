package com.kuit.youthroulette.data.remote.dto
//서버가 에러 응답(4xx, 5xx) 시 내려주는 공통 에러 바디, 명세서에 맞게 필드명 맞춰야함
data class ErrorResponse(
    val code: String,
    val message: String,
    val status: Int,
    val errors: List<FieldError>? = null
)

data class FieldError(
    val field: String,
    val reason: String
)
