package com.kuit.youthroulette.data.mapper
import com.kuit.youthroulette.data.remote.dto.PostDto
import com.kuit.youthroulette.model.FeedPost
import com.kuit.youthroulette.ui.friend.FeedUiModel

private val bucketEmojis = listOf("🏞️", "🍜", "🏮", "🌊", "🎡", "🧗", "🧳", "✨")

private fun bucketEmojiFor(seed: Int): String {
    val index = ((seed % bucketEmojis.size) + bucketEmojis.size) % bucketEmojis.size
    return bucketEmojis[index]
}

private fun formatTime(createdAt: String?): String {
    if (createdAt.isNullOrBlank()) return ""
    return createdAt.take(10)
}

// 결과/인증 완료 탭에서 사용하는 기존 FeedPost 모델
fun PostDto.toFeedPost(): FeedPost {
    return FeedPost(
        id = postId,
        bucketId = bucketId,
        bucketTitle = bucketTitle,
        content = reviewText.orEmpty(),
        imageUrl = imageUrl,
        isPublic = visibility.toIsPublic(),
        taggedFriendNames = emptyList(),
        createdAt = createdAt.orEmpty()
    )
}

// 친구 탭 피드에서 사용하는 FeedUiModel
fun PostDto.toFeedUiModel(): FeedUiModel {
    val text = reviewText?.takeIf { it.isNotBlank() } ?: bucketTitle

    return FeedUiModel(
        id = postId,
        name = nickname,
        avatarEmoji = avatarEmojiFor(userId),
        message = "'$bucketTitle' 완료! $text",
        time = formatTime(createdAt),
        likeCount = likeCount,
        bucketEmoji = bucketEmojiFor(bucketId),
        isLiked = likedByMe
    )
}

private fun String?.toIsPublic(): Boolean {
    return when (this) {
        "PUBLIC" -> true
        "PRIVATE" -> false
        else -> true
    }
}