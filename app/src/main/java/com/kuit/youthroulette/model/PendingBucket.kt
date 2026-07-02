package com.kuit.youthroulette.model

data class PendingBucket(

    val id: Int,

    val bucketId: Int,

    val title: String,

    val date: String,

    val content: String,

    val taggedFriendNames: List<String> = emptyList(),

    val category: String = ""

)