package com.kuit.youthroulette.data.mapper

import com.kuit.youthroulette.data.remote.dto.PostDto
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

fun PostDto.toFeedUiModel(): FeedUiModel {
    val text = reviewText?.takeIf { it.isNotBlank() } ?: bucketTitle
    return FeedUiModel(
        id = postId,
        name = nickname,
        avatarEmoji = profileEmojiFor(emojiIndex, userId),
        message = "'$bucketTitle' 완료! $text",
        time = formatTime(createdAt),
        likeCount = likeCount,
        bucketEmoji = bucketEmojiFor(bucketId),
        isLiked = likedByMe
    )
}
