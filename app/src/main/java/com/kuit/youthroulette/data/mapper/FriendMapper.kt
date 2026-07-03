package com.kuit.youthroulette.data.mapper

import com.kuit.youthroulette.data.remote.dto.FriendDto
import com.kuit.youthroulette.data.remote.dto.FriendRequestDto
import com.kuit.youthroulette.ui.friend.FriendRequestUiModel
import com.kuit.youthroulette.ui.friend.FriendUiModel
import com.kuit.youthroulette.ui.mypage.profileEmojiOptions

private val avatarEmojis = listOf("🙂", "😎", "🥳", "🐣", "🐱", "🦊", "🌟", "🍀")

internal fun avatarEmojiFor(seed: Int): String {
    val index = ((seed % avatarEmojis.size) + avatarEmojis.size) % avatarEmojis.size
    return avatarEmojis[index]
}

internal fun profileEmojiFor(emojiIndex: Int?, fallbackSeed: Int): String {
    if (emojiIndex != null && emojiIndex in profileEmojiOptions.indices) {
        return profileEmojiOptions[emojiIndex]
    }
    return avatarEmojiFor(fallbackSeed)
}

fun FriendDto.toFriendUiModel(): FriendUiModel {
    return FriendUiModel(
        id = friendId,
        name = nickname,
        avatarEmoji = profileEmojiFor(emojiIndex, userId)
    )
}

fun FriendRequestDto.toFriendRequestUiModel(): FriendRequestUiModel {
    return FriendRequestUiModel(
        id = friendId,
        name = requesterNickname,
        userId = ""
    )
}
