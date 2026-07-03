package com.kuit.youthroulette.data.remote.dto

data class TaggedUserDto(
    val userId: Int,
    val nickname: String
)

data class PostDto(
    val postId: Int,
    val userId: Int,
    val nickname: String,
    val emojiIndex: Int? = null,
    val colorIndex: Int? = null,
    val bucketId: Int,
    val bucketTitle: String,
    val imageUrl: String? = null,
    val reviewText: String? = null,
    val visibility: String? = null,
    val likeCount: Int = 0,
    val likedByMe: Boolean = false,
    val taggedUsers: List<TaggedUserDto> = emptyList(),
    val taggedFriends: List<TaggedUserDto> = emptyList(),
    val createdAt: String? = null,
    val updatedAt: String? = null
)

data class LikeResponse(
    val postId: Int,
    val likedByMe: Boolean,
    val likeCount: Int
)

data class DeletePostResponse(
    val postId: Int,
    val message: String? = null
)
