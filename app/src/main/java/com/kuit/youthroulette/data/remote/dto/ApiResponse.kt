package com.kuit.youthroulette.data.remote.dto

data class ErrorResponse(
    val code: String? = null,
    val message: String? = null,
    val status: Int? = null,
    val errors: List<FieldError>? = null
)

data class FieldError(
    val field: String? = null,
    val reason: String? = null
)
