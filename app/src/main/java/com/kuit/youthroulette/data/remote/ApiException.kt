package com.kuit.youthroulette.data.remote
//서버 에러(4xx, 5xx) 응답을 ErrorResponse로 파싱해서 던지는 예외
import com.google.gson.Gson
import com.kuit.youthroulette.data.remote.dto.ErrorResponse
import retrofit2.HttpException
import retrofit2.Response

class ApiException(val errorResponse: ErrorResponse) : Exception(errorResponse.message)

fun HttpException.toApiException(): ApiException {
    return response()?.toApiException() ?: ApiException(
        ErrorResponse(
            code = "UNKNOWN_ERROR",
            message = message() ?: "알 수 없는 오류가 발생했습니다.",
            status = code()
        )
    )
}

fun Response<*>.toApiException(): ApiException {
    val parsed = errorBody()?.string()?.let {
        runCatching { Gson().fromJson(it, ErrorResponse::class.java) }.getOrNull()
    }
    return ApiException(
        parsed ?: ErrorResponse(
            code = "UNKNOWN_ERROR",
            message = message().ifEmpty { "요청을 처리하지 못했습니다." },
            status = code()
        )
    )
}
