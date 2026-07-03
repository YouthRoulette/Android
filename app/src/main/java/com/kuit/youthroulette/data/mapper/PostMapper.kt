package com.kuit.youthroulette.data.mapper
//PostDto를 기존 FeedPost 모델로 바꾸는 파일, 기존 FeedPost 모델 생성자에 맞춰 수정
import com.kuit.youthroulette.data.remote.dto.PostDto
import com.kuit.youthroulette.model.FeedPost

fun PostDto.toFeedPost(): FeedPost {
    return FeedPost(
        id = postId,
        bucketId = bucketId,
        bucketTitle = bucketTitle,
        content = reviewText.orEmpty(),
        imageUrl = imageUrl,
        isPublic = visibility.toIsPublic(),
        taggedFriendNames = emptyList(),
        createdAt = ""
    )
}

private fun String?.toIsPublic(): Boolean {
    return when (this) {
        "PUBLIC" -> true
        "PRIVATE" -> false
        else -> true
    }
}