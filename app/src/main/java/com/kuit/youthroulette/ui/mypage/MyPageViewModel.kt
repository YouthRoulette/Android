package com.kuit.youthroulette.ui.mypage

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.youthroulette.data.BucketRepository
import com.kuit.youthroulette.data.SessionManager
import com.kuit.youthroulette.data.UserRepository
import com.kuit.youthroulette.model.BucketStatus
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
        observeBucketCounts()
    }

    fun loadMyInfo() {
        viewModelScope.launch {
            UserRepository.getMyInfo().onSuccess { user ->
                _uiState.update {
                    it.copy(
                        nickname = user.nickname,
                        userId = user.loginId,
                        profileEmojiIndex = user.emojiIndex.coerceIn(0, profileEmojiOptions.lastIndex),
                        profileColorIndex = user.colorIndex
                    )
                }
                SessionManager.updateAuth(null, user.loginId, user.nickname)
            }
        }
    }

    private fun observeBucketCounts() {
        viewModelScope.launch {
            snapshotFlow {
                val items = BucketRepository.bucketItems
                val challenged = items.count {
                    it.status == BucketStatus.IN_PROGRESS || it.status == BucketStatus.COMPLETED
                }
                val completed = items.count { it.status == BucketStatus.COMPLETED }
                challenged to completed
            }.collect { (challenged, completed) ->
                _uiState.update { it.copy(challengedCount = challenged, completedCount = completed) }
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
