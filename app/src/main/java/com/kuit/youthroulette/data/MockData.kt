package com.kuit.youthroulette.data

import com.kuit.youthroulette.model.BucketItem
import com.kuit.youthroulette.model.BucketStatus
import com.kuit.youthroulette.ui.friend.FeedUiModel
import com.kuit.youthroulette.ui.friend.FriendRequestUiModel
import com.kuit.youthroulette.ui.friend.FriendUiModel

object MockData {

    val bucketItems: List<BucketItem> = listOf(
        BucketItem(
            id = 1,
            title = "피크닉 가기",
            emojiIndex = 0,
            status = BucketStatus.NOT_STARTED,
            colorIndex = 1
        ),
        BucketItem(
            id = 2,
            title = "일출 보기",
            emojiIndex = 1,
            status = BucketStatus.IN_PROGRESS,
            colorIndex = 2
        ),
        BucketItem(
            id = 3,
            title = "캠핑 가기",
            emojiIndex = 2,
            status = BucketStatus.NOT_STARTED,
            colorIndex = 3
        ),
        BucketItem(
            id = 4,
            title = "한강에서 치맥하기",
            emojiIndex = 3,
            status = BucketStatus.NOT_STARTED,
            colorIndex = 4
        ),
        BucketItem(
            id = 5,
            title = "밤바다 보기",
            emojiIndex = 4,
            status = BucketStatus.NOT_STARTED,
            colorIndex = 5
        ),
        BucketItem(
            id = 6,
            title = "번지점프 도전",
            emojiIndex = 5,
            status = BucketStatus.NOT_STARTED,
            colorIndex = 6
        ),
        BucketItem(
            id = 7,
            title = "혼자 여행 가기",
            emojiIndex = 6,
            status = BucketStatus.NOT_STARTED,
            colorIndex = 7
        ),
        BucketItem(
            id = 8,
            title = "오로라 보러 가기",
            emojiIndex = 7,
            status = BucketStatus.NOT_STARTED,
            colorIndex = 0
        )
    )

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
