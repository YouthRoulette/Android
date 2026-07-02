package com.kuit.youthroulette.ui.mypage

import androidx.compose.ui.graphics.Color

// ───────── 색상 (프로토타입 테마) ─────────
internal val ScreenBackground = Color(0xFFFFFBF7)
internal val AccentOrange = Color(0xFFD27A45)
internal val StatValueColor = Color(0xFFEE8A3D)
internal val MutedText = Color(0xFFA6907F)
internal val AvatarBackground = Color(0xFFFFE3D0)
internal val CardBackground = Color.White
internal val CardBorderColor = Color(0xFFF0EDE8)
internal val SelectedBorderColor = Color(0xFF2B2B2B)
internal val EmojiSlotBackground = Color(0xFFF4F2EF)

// 프로필 사진 이모지 (버킷 이모지와 동일한 방식으로 하드코딩)
val ProfileEmojiOptions = listOf(
    "🙂", "😎", "🥳", "🐣", "🐱", "🦊", "🌟", "🍀"
)

// ───────── 화면 상태 ─────────
data class MyPageUiState(
    val nickname: String = "청춘이",
    val userId: String = "youth_mate",
    val profileEmojiIndex: Int = 0,
    val challengedCount: Int = 0,
    val completedCount: Int = 0
)
