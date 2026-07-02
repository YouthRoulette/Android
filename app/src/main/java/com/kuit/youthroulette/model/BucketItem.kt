package com.kuit.youthroulette.model

data class BucketItem(
    val id: Int,

    val title: String,

    val content: String = "",

    val category: String = "",

    val date: String = "",

    val status: BucketStatus = BucketStatus.NOT_STARTED,

    val taggedFriendNames: List<String> = emptyList(),

    val emoji: String = "🎯"
)