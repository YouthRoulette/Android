package com.kuit.youthroulette.data.remote

import com.kuit.youthroulette.data.remote.dto.FriendDto
import com.kuit.youthroulette.data.remote.dto.FriendRequestCreate
import com.kuit.youthroulette.data.remote.dto.FriendRequestDto
import com.kuit.youthroulette.data.remote.dto.LikeResponse
import com.kuit.youthroulette.data.remote.dto.LoginRequest
import com.kuit.youthroulette.data.remote.dto.LoginResponse
import com.kuit.youthroulette.data.remote.dto.NicknameUpdateRequest
import com.kuit.youthroulette.data.remote.dto.NicknameUpdateResponse
import com.kuit.youthroulette.data.remote.dto.PostDto
import com.kuit.youthroulette.data.remote.dto.ProfileUpdateRequest
import com.kuit.youthroulette.data.remote.dto.ProfileUpdateResponse
import com.kuit.youthroulette.data.remote.dto.SignUpRequest
import com.kuit.youthroulette.data.remote.dto.SignUpResponse
import com.kuit.youthroulette.data.remote.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface YouthRouletteApi {

    @POST("api/auth/signup")
    suspend fun signUp(@Body request: SignUpRequest): SignUpResponse

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("api/users/me")
    suspend fun getMyInfo(@Header("Authorization") token: String): UserDto

    @PATCH("api/users/nickname")
    suspend fun updateNickname(
        @Header("Authorization") token: String,
        @Body request: NicknameUpdateRequest
    ): NicknameUpdateResponse

    @PATCH("api/users/profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body request: ProfileUpdateRequest
    ): ProfileUpdateResponse

    @GET("api/friends")
    suspend fun getFriends(@Header("Authorization") token: String): List<FriendDto>

    @GET("api/friends/requests/received")
    suspend fun getReceivedRequests(@Header("Authorization") token: String): List<FriendRequestDto>

    @POST("api/friends/request")
    suspend fun sendFriendRequest(
        @Header("Authorization") token: String,
        @Body request: FriendRequestCreate
    ): FriendRequestDto

    @PATCH("api/friends/{friendId}/accept")
    suspend fun acceptFriendRequest(
        @Header("Authorization") token: String,
        @Path("friendId") friendId: Int
    ): FriendRequestDto

    @PATCH("api/friends/{friendId}/reject")
    suspend fun rejectFriendRequest(
        @Header("Authorization") token: String,
        @Path("friendId") friendId: Int
    ): FriendRequestDto

    @GET("api/posts/feed")
    suspend fun getFeed(@Header("Authorization") token: String): List<PostDto>

    @GET("api/posts/me")
    suspend fun getMyPosts(@Header("Authorization") token: String): List<PostDto>

    @POST("api/posts/{postId}/likes")
    suspend fun likePost(
        @Header("Authorization") token: String,
        @Path("postId") postId: Int
    ): LikeResponse

    @DELETE("api/posts/{postId}/likes")
    suspend fun unlikePost(
        @Header("Authorization") token: String,
        @Path("postId") postId: Int
    ): LikeResponse
}
