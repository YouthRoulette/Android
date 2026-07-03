package com.kuit.youthroulette.data

import com.kuit.youthroulette.data.BucketRepository.bucketItems
import com.kuit.youthroulette.model.BucketStatus
import com.kuit.youthroulette.ui.friend.FeedUiModel
import com.kuit.youthroulette.ui.friend.FriendRequestUiModel
import com.kuit.youthroulette.ui.friend.FriendUiModel

object MockData {

    // 친구 -------------------------------------------------------------------------

    val friends: List<FriendUiModel> = listOf(
        FriendUiModel(id = 1, name = "민수", avatarEmoji = "🙂"),
        FriendUiModel(id = 2, name = "지연", avatarEmoji = "😎"),
        FriendUiModel(id = 3, name = "영희", avatarEmoji = "😘"),
        FriendUiModel(id = 4, name = "한우", avatarEmoji = "😴")
    )

    val feeds: List<FeedUiModel> = listOf(
        FeedUiModel(
            id = 1,
            name = "민수",
            avatarEmoji = "🙂",
            message = "'계곡 가기' 완료! 시원해서 힐링됐어요 💧",
            time = "2시간 전",
            likeCount = 24,
            bucketEmoji = "🏞️"
        ),
        FeedUiModel(
            id = 2,
            name = "지연",
            avatarEmoji = "😎",
            message = "'맛집 탐방하기' 완료! 맛있는 거 많이 먹었다 🍜",
            time = "5시간 전",
            likeCount = 31,
            bucketEmoji = "🍜"
        ),
        FeedUiModel(
            id = 3,
            name = "영희",
            avatarEmoji = "😘",
            message = "'야시장 가기' 완료! 사람도 많고 재밌었어 ✨",
            time = "1일 전",
            likeCount = 18,
            bucketEmoji = "🏮"
        )
    )

    val friendRequests: List<FriendRequestUiModel> = listOf(
        FriendRequestUiModel(id = 1, name = "수아", userId = "sua_youth")
    )

    // 친구 -------------------------------------------------------------------------

    val profileEmojiOptions: List<String> = listOf(
        "🙂", "😎", "🥳", "🐣", "🐱", "🦊", "🌟", "🍀"
    )

    const val challengedCount: Int = 12
    val completedCount: Int = bucketItems.count { it.status == BucketStatus.COMPLETED }
}
