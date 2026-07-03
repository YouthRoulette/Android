package com.kuit.youthroulette.data.remote.dto//친구 목록 조회 API의 서버 응답을 담는 클래스 코드예시, 서버 명세에 맞게 필드명 맞춰야함

data class FriendDto(
    val friendId: Int,
    val nickname: String,
    val profileImageUrl: String? = null
)