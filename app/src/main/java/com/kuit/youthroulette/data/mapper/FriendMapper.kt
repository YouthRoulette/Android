package com.kuit.youthroulette.data.mapper
import com.kuit.youthroulette.data.remote.dto.FriendDto
import com.kuit.youthroulette.data.remote.dto.FriendRequestDto
import com.kuit.youthroulette.model.Friend
import com.kuit.youthroulette.ui.friend.FriendRequestUiModel
import com.kuit.youthroulette.ui.friend.FriendUiModel

private val avatarEmojis = listOf("🙂", "😎", "🥳", "🐣", "🐱", "🦊", "🌟", "🍀")

internal fun avatarEmojiFor(seed: Int): String {
    val index = ((seed % avatarEmojis.size) + avatarEmojis.size) % avatarEmojis.size
    return avatarEmojis[index]
}

// 인증 화면 친구 태그에서 사용하는 모델
fun FriendDto.toFriend(): Friend {
    return Friend(
        id = friendId,
        name = nickname,
        profileImageUrl = null,
        statusMessage = ""
    )
}

// 친구 탭 친구 목록에서 사용하는 모델
fun FriendDto.toFriendUiModel(): FriendUiModel {
    return FriendUiModel(
        id = friendId,
        name = nickname,
        avatarEmoji = avatarEmojiFor(userId)
    )
}

// 받은 친구 요청 목록에서 사용하는 모델
fun FriendRequestDto.toFriendRequestUiModel(): FriendRequestUiModel {
    return FriendRequestUiModel(
        id = friendId,
        name = requesterNickname,
        userId = ""
    )
}