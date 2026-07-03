package com.kuit.youthroulette.data.remote

import com.kuit.youthroulette.data.remote.dto.CreatePostRequest
import com.kuit.youthroulette.data.remote.dto.DeletePostResponse
import com.kuit.youthroulette.data.remote.dto.FriendDto
import com.kuit.youthroulette.data.remote.dto.FriendRequestCreate
import com.kuit.youthroulette.data.remote.dto.FriendRequestDto
import com.kuit.youthroulette.data.remote.dto.LikeResponse
import com.kuit.youthroulette.data.remote.dto.LoginRequest
import com.kuit.youthroulette.data.remote.dto.LoginResponse
import com.kuit.youthroulette.data.remote.dto.NicknameUpdateRequest
import com.kuit.youthroulette.data.remote.dto.NicknameUpdateResponse
import com.kuit.youthroulette.data.remote.dto.PostDto
import com.kuit.youthroulette.data.remote.dto.PresignedUrlRequest
import com.kuit.youthroulette.data.remote.dto.PresignedUrlResponse
import com.kuit.youthroulette.data.remote.dto.ProfileUpdateRequest
import com.kuit.youthroulette.data.remote.dto.ProfileUpdateResponse
import com.kuit.youthroulette.data.remote.dto.SignUpRequest
import com.kuit.youthroulette.data.remote.dto.SignUpResponse
import com.kuit.youthroulette.data.remote.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface YouthRouletteApi {

    @POST("api/auth/signup")
    suspend fun signUp(@Body request: SignUpRequest): SignUpResponse

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("api/users/me")
    suspend fun getMyInfo(): UserDto

    @PATCH("api/users/nickname")
    suspend fun updateNickname(@Body request: NicknameUpdateRequest): NicknameUpdateResponse

    @PATCH("api/users/profile")
    suspend fun updateProfile(@Body request: ProfileUpdateRequest): ProfileUpdateResponse

    @GET("api/friends")
    suspend fun getFriends(): List<FriendDto>

    @GET("api/friends/requests/received")
    suspend fun getReceivedRequests(): List<FriendRequestDto>

    @POST("api/friends/request")
    suspend fun sendFriendRequest(@Body request: FriendRequestCreate): FriendRequestDto

    @PATCH("api/friends/{friendId}/accept")
    suspend fun acceptFriendRequest(@Path("friendId") friendId: Int): FriendRequestDto

    @PATCH("api/friends/{friendId}/reject")
    suspend fun rejectFriendRequest(@Path("friendId") friendId: Int): FriendRequestDto

    @GET("api/posts/feed")
    suspend fun getFeed(): List<PostDto>

    @GET("api/posts/me")
    suspend fun getMyPosts(): List<PostDto>

    @POST("api/posts/{bucketId}")
    suspend fun createPost(
        @Path("bucketId") bucketId: Int,
        @Body request: CreatePostRequest
    ): PostDto

    @DELETE("api/posts/{postId}")
    suspend fun deletePost(@Path("postId") postId: Int): DeletePostResponse

    @POST("api/posts/{postId}/likes")
    suspend fun likePost(@Path("postId") postId: Int): LikeResponse

    @DELETE("api/posts/{postId}/likes")
    suspend fun unlikePost(@Path("postId") postId: Int): LikeResponse

    @POST("api/images/presigned-url")
    suspend fun getPresignedUrl(@Body request: PresignedUrlRequest): PresignedUrlResponse
}
