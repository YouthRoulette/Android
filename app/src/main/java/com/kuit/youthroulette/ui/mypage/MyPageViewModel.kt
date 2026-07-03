package com.kuit.youthroulette.ui.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.youthroulette.data.SessionManager
import com.kuit.youthroulette.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyPageViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        MyPageUiState(
            nickname = SessionManager.nickname.orEmpty(),
            userId = SessionManager.loginId.orEmpty()
        )
    )
    val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

    init {
        loadMyInfo()
    }

    fun loadMyInfo() {
        viewModelScope.launch {
            UserRepository.getMyInfo()
                .onSuccess { user ->
                    Log.d("MyPageViewModel", "getMyInfo: challengedCount=${user.challengedCount}, completedCount=${user.completedCount}")
                    _uiState.update {
                        it.copy(
                            nickname = user.nickname,
                            userId = user.loginId,
                            profileEmojiIndex = user.emojiIndex.coerceIn(0, profileEmojiOptions.lastIndex),
                            profileColorIndex = user.colorIndex,
                            challengedCount = user.challengedCount,
                            completedCount = user.completedCount
                        )
                    }
                    SessionManager.updateAuth(null, user.loginId, user.nickname)
                }
                .onFailure { error ->
                    // 실패 시 이전 값을 그대로 유지하면 "숫자가 실제 데이터와 안 맞는" 것처럼 보일 수 있어 로그로 남김
                    Log.e("MyPageViewModel", "getMyInfo failed, keeping stale counts", error)
                }
        }
    }

    fun changeNickname(newNickname: String) {
        val trimmed = newNickname.trim()
        if (trimmed.isBlank()) return
        viewModelScope.launch {
            UserRepository.updateNickname(trimmed)
            _uiState.update { it.copy(nickname = trimmed) }
            SessionManager.updateNickname(trimmed)
        }
    }

    fun changeProfileEmoji(index: Int) {
        viewModelScope.launch {
            UserRepository.updateProfile(index, _uiState.value.profileColorIndex)
            _uiState.update { it.copy(profileEmojiIndex = index) }
        }
    }

    fun logout() {
        SessionManager.clear()
    }
}
