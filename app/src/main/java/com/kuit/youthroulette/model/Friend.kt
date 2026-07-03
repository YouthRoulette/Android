package com.kuit.youthroulette.model

data class Friend(

    val id: Int,

    val name: String,

    val profileImageUrl: String? = null,

    val statusMessage: String = ""

)