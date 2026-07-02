package com.kuit.youthroulette.ui.friend

import androidx.compose.ui.graphics.Color

// ───────── 색상 (프로토타입 테마) ─────────
internal val ScreenBackground = Color(0xFFFFFBF7)
internal val TabBackground = Color(0xFFF7E3CF)
internal val AccentOrange = Color(0xFFD27A45)
internal val MutedText = Color(0xFFA6907F)
internal val CardBackground = Color.White
internal val CardBorderColor = Color(0xFFF0EDE8)
internal val AvatarBackground = Color(0xFFFFE3D0)
internal val RequestBannerBackground = Color(0xFFFFEFC2)
internal val RequestBannerText = Color(0xFFB9822A)
internal val AcceptButtonColor = Color(0xFF3F8F6C)
internal val AddButtonColor = Color(0xFFEE8A3D)

// 소식 썸네일(친구가 완료한 버킷 이모지) 배경 색상 팔레트
internal val FeedThumbPalette = listOf(
    Color(0xFFFFE3D0),
    Color(0xFFFCE0E8),
    Color(0xFFE4DBFA),
    Color(0xFFCDEEDD),
    Color(0xFFDCEAFB)
)

// ───────── UI 모델 ─────────
data class FriendUiModel(
    val id: Int,
    val name: String,
    val avatarEmoji: String
)

data class FeedUiModel(
    val id: Int,
    val name: String,
    val avatarEmoji: String,
    val message: String,
    val time: String,
    val likeCount: Int,
    val bucketEmoji: String
)

data class FriendRequestUiModel(
    val id: String,
    val name: String,
    val userId: String
)

// 친구 / 소식
enum class FriendTab(val label: String) {
    LIST("친구"),
    NEWS("소식")
}

// ───────── 화면 상태 ─────────
data class FriendUiState(
    val friends: List<FriendUiModel> = emptyList(),
    val feeds: List<FeedUiModel> = emptyList(),
    val pendingRequests: List<FriendRequestUiModel> = emptyList()
)
