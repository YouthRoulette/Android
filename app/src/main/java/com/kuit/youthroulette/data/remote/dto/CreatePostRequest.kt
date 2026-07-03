package com.kuit.youthroulette.data.remote.dto
//완료 인증하기 버튼을 눌렀을 때 서버로 보내는 요청 body, 서버에서 필드명이 다르면 바꿔야함
data class CreatePostRequest(
    val imageUrl: String,
    val reviewText: String,
    val visibility: String,
    val friendIds: List<Int>
)