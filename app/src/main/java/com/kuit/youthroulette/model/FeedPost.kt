package com.kuit.youthroulette.model

data class FeedPost(

    val id: Int,

    val bucketId: Int,

    val writerName: String? = null,

    val bucketTitle: String,

    val content: String,

    val imageUrl: String? = null,

    val isPublic: Boolean = true,

    val taggedFriendNames: List<String> = emptyList(),

    val likeCount: Int = 0,

    val commentCount: Int = 0,

    val createdAt: String

)