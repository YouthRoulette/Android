package com.kuit.youthroulette.data.remote//실제 서버 API 명세를 적는 파일, 명세서에 맞게 수정필요

import com.kuit.youthroulette.data.remote.dto.BucketDto
import com.kuit.youthroulette.data.remote.dto.CreateBucketRequest
import com.kuit.youthroulette.data.remote.dto.LoginRequest
import com.kuit.youthroulette.data.remote.dto.LoginResponse
import com.kuit.youthroulette.data.remote.dto.SignupRequest
import com.kuit.youthroulette.data.remote.dto.SignupResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface YouthRouletteApi {

    // 회원가입
    @POST("api/auth/signup")
    suspend fun signup(@Body request: SignupRequest): SignupResponse

    // 로그인
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    // 내 버킷 목록 조회
    @GET("api/buckets")
    suspend fun getBuckets(
        @Query("status") status: String? = null,
        @Query("verified") verified: Boolean? = null
    ): List<BucketDto>

    // 버킷 생성
    @POST("api/buckets")
    suspend fun createBucket(@Body request: CreateBucketRequest): BucketDto

    // 버킷 삭제
    // 응답 바디가 없거나(204) 형식이 다를 수 있어 Gson 파싱 없이 원본 Response로 받음
    @DELETE("api/buckets/{bucketId}")
    suspend fun deleteBucket(@Path("bucketId") bucketId: Int): Response<ResponseBody>

    // 룰렛 돌리기 (서버가 미완료 버킷 중 하나를 골라 반환)
    @POST("api/buckets/roulette")
    suspend fun spinRoulette(): BucketDto

    // 도전 시작
    @PATCH("api/buckets/{bucketId}/start")
    suspend fun startBucket(@Path("bucketId") bucketId: Int): BucketDto

    // 버킷 미완료 처리
    @PATCH("api/buckets/{bucketId}/incomplete")
    suspend fun incompleteBucket(@Path("bucketId") bucketId: Int): BucketDto

    // 버킷 완료 처리
    @PATCH("api/buckets/{bucketId}/complete")
    suspend fun completeBucket(@Path("bucketId") bucketId: Int): BucketDto
}
