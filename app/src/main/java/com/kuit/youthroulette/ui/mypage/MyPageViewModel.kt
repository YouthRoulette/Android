package com.kuit.youthroulette.ui.mypage

import androidx.lifecycle.ViewModel
import com.kuit.youthroulette.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MyPageViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        MyPageUiState(
            // 로그인 시 저장한 유저 정보 (아이디 / 닉네임)
            nickname = UserRepository.getCurrentUser()?.nickname ?: "청춘이",
            userId = UserRepository.getCurrentUser()?.id ?: "youth_mate",
            profileEmojiIndex = 0,
            // 통계 (하드코딩)
            challengedCount = 12,
            completedCount = 8
        )
    )
    val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

    // 닉네임 변경
    fun changeNickname(newNickname: String) {
        val trimmed = newNickname.trim()
        if (trimmed.isBlank()) return
        _uiState.update { it.copy(nickname = trimmed) }
    }

    // 프로필 이모지 변경
    fun changeProfileEmoji(index: Int) {
        _uiState.update { it.copy(profileEmojiIndex = index) }
    }
}
