package com.kuit.youthroulette.data.remote.dto//서버에서 받은 버킷 JSON을 담는 클래스 코드예시, 서버 명세에 맞게 필드명 맞춰야함

data class BucketDto(
    val bucketId: Int,
    val title: String,
    val status: String? = null
)