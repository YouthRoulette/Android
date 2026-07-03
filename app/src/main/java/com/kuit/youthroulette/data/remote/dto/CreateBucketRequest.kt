package com.kuit.youthroulette.data.remote.dto
//버킷 생성하기 버튼을 눌렀을 때 서버로 보내는 요청 body, 서버에서 필드명이 다르면 바꿔야함
data class CreateBucketRequest(
    val title: String,
    val emojiIndex: Int,
    val colorIndex: Int
)
