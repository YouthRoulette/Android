package com.kuit.youthroulette.data.remote//실제 서버 API 명세를 적는 파일, 명세서에 맞게 수정필요

import com.kuit.youthroulette.data.remote.dto.BucketDto
import com.kuit.youthroulette.data.remote.dto.CreatePostRequest
import com.kuit.youthroulette.data.remote.dto.FriendDto
import com.kuit.youthroulette.data.remote.dto.PostDto
import com.kuit.youthroulette.data.remote.dto.PresignedUrlRequest
import com.kuit.youthroulette.data.remote.dto.PresignedUrlResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface YouthRouletteApi {

    // 미완료 탭: 인증 필요한 버킷 조회
    @GET("api/buckets/pending")
    suspend fun getPendingBuckets(): Response<List<BucketDto>>

    // 인증 완료 탭: 완료된 인증/피드 조회
    @GET("api/posts/completed")
    suspend fun getCompletedPosts(): Response<List<PostDto>>

    // 친구 태그: 친구 목록 조회
    @GET("api/friends")
    suspend fun getFriends(): Response<List<FriendDto>>

    // 완료 인증하기
    @POST("api/posts/{bucketId}")
    suspend fun createPost(
        @Path("bucketId") bucketId: Int,
        @Body request: CreatePostRequest
    ): Response<PostDto>

    @POST("api/images/presigned-url")
    suspend fun getPresignedUrl(
        @Body request: PresignedUrlRequest
    ): Response<PresignedUrlResponse>
}