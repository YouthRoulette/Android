package com.kuit.youthroulette.data.remote.dto
//인증 완료된 게시글, 피드, 완료 버킷 정보를 서버에서 받을 때 쓰는 DTO, 완료 탭에서 서버가 어떤 필드를 주는지에 따라 수정
data class PostDto(
    val postId: Int,
    val bucketId: Int,
    val bucketTitle: String,
    val imageUrl: String?,
    val reviewText: String?,
    val visibility: String?,
    val friendIds: List<Int>? = emptyList()
)