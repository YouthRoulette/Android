package com.kuit.youthroulette.ui.mypage

import androidx.compose.ui.graphics.Color

internal val ScreenBackground = Color(0xFFFFFBF7)
internal val AccentOrange = Color(0xFFD27A45)
internal val StatValueColor = Color(0xFFEE8A3D)
internal val MutedText = Color(0xFFA6907F)
internal val AvatarBackground = Color(0xFFFFE3D0)
internal val CardBackground = Color.White
internal val CardBorderColor = Color(0xFFF0EDE8)
internal val SelectedBorderColor = Color(0xFF2B2B2B)
internal val EmojiSlotBackground = Color(0xFFF4F2EF)

internal val profileEmojiOptions: List<String> = listOf(
    "🙂", "😎", "🥳", "🐣", "🐱", "🦊", "🌟", "🍀"
)

data class MyPageUiState(
    val nickname: String = "",
    val userId: String = "",
    val profileEmojiIndex: Int = 0,
    val profileColorIndex: Int = 0,
    val challengedCount: Int = 0,
    val completedCount: Int = 0
)
