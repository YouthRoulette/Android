package com.kuit.youthroulette.model

data class BucketItem(
    val id: Int,

    val title: String,

    val date: String = "",

    val status: BucketStatus = BucketStatus.NOT_STARTED,

    val taggedFriendNames: List<String> = emptyList(),

    val emojiIndex: Int = 0,

    val colorIndex: Int = 0
)