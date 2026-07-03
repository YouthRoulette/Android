import com.kuit.youthroulette.data.remote.dto.BucketDto
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

    // 회원가입
    @POST("api/auth/signup")
    suspend fun signUp(@Body request: SignUpRequest): SignUpResponse

    // 로그인
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    // 내 정보 조회
    @GET("api/users/me")
    suspend fun getMyInfo(): UserDto

    // 닉네임 수정
    @PATCH("api/users/nickname")
    suspend fun updateNickname(@Body request: NicknameUpdateRequest): NicknameUpdateResponse

    // 프로필 수정
    @PATCH("api/users/profile")
    suspend fun updateProfile(@Body request: ProfileUpdateRequest): ProfileUpdateResponse

    // 친구 목록 조회
    @GET("api/friends")
    suspend fun getFriends(): List<FriendDto>

    // 받은 친구 요청 조회
    @GET("api/friends/requests/received")
    suspend fun getReceivedRequests(): List<FriendRequestDto>

    // 친구 요청 보내기
    @POST("api/friends/request")
    suspend fun sendFriendRequest(@Body request: FriendRequestCreate): FriendRequestDto

    // 친구 요청 수락
    @PATCH("api/friends/{friendId}/accept")
    suspend fun acceptFriendRequest(@Path("friendId") friendId: Int): FriendRequestDto

    // 친구 요청 거절
    @PATCH("api/friends/{friendId}/reject")
    suspend fun rejectFriendRequest(@Path("friendId") friendId: Int): FriendRequestDto

    // 미완료 탭: 인증 필요한 버킷 조회
    @GET("api/buckets/pending")
    suspend fun getPendingBuckets(): List<BucketDto>

    // 인증 완료 탭: 완료된 인증/피드 조회
    @GET("api/posts/completed")
    suspend fun getCompletedPosts(): List<PostDto>

    // 친구 탭 피드 조회
    @GET("api/posts/feed")
    suspend fun getFeed(): List<PostDto>

    // 내 게시글 조회
    @GET("api/posts/me")
    suspend fun getMyPosts(): List<PostDto>

    // 완료 인증하기
    @POST("api/posts/{bucketId}")
    suspend fun createPost(
        @Path("bucketId") bucketId: Int,
        @Body request: CreatePostRequest
    ): PostDto

    // 게시글 삭제
    @DELETE("api/posts/{postId}")
    suspend fun deletePost(@Path("postId") postId: Int): DeletePostResponse

    // 좋아요
    @POST("api/posts/{postId}/likes")
    suspend fun likePost(@Path("postId") postId: Int): LikeResponse

    // 좋아요 취소
    @DELETE("api/posts/{postId}/likes")
    suspend fun unlikePost(@Path("postId") postId: Int): LikeResponse

    // 이미지 업로드용 presigned url 발급
    @POST("api/images/presigned-url")
    suspend fun getPresignedUrl(
        @Body request: PresignedUrlRequest
    ): PresignedUrlResponse
}