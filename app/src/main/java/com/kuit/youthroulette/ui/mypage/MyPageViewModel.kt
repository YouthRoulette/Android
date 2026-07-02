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
            nickname = UserRepository.getCurrentUser()?.nickname ?: "청춘이",
            userId = UserRepository.getCurrentUser()?.id ?: "youth_mate",
            profileEmojiIndex = 0,
            challengedCount = 12,
            completedCount = 8
        )
    )
    val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

    fun changeNickname(newNickname: String) {
        val trimmed = newNickname.trim()
        if (trimmed.isBlank()) return
        _uiState.update { it.copy(nickname = trimmed) }
    }

    fun changeProfileEmoji(index: Int) {
        _uiState.update { it.copy(profileEmojiIndex = index) }
    }
}
