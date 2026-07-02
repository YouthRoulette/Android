package com.kuit.youthroulette.ui.friend

import androidx.lifecycle.ViewModel
import com.kuit.youthroulette.data.MockData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FriendViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        FriendUiState(
            friends = MockData.friends,
            feeds = MockData.feeds,
            pendingRequests = MockData.friendRequests
        )
    )
    val uiState: StateFlow<FriendUiState> = _uiState.asStateFlow()

    fun acceptRequest(request: FriendRequestUiModel) {
        _uiState.update { state ->
            val newId = (state.friends.maxOfOrNull { it.id } ?: 0) + 1
            state.copy(
                pendingRequests = state.pendingRequests.filterNot { it.id == request.id },
                friends = listOf(
                    FriendUiModel(id = newId, name = request.name, avatarEmoji = "🙂")
                ) + state.friends
            )
        }
    }

    fun toggleLike(feedId: Int) {
        _uiState.update { state ->
            state.copy(
                feeds = state.feeds.map { feed ->
                    if (feed.id != feedId) {
                        feed
                    } else if (feed.isLiked) {
                        feed.copy(isLiked = false, likeCount = feed.likeCount - 1)
                    } else {
                        feed.copy(isLiked = true, likeCount = feed.likeCount + 1)
                    }
                }
            )
        }
    }

    fun rejectRequest(request: FriendRequestUiModel) {
        _uiState.update { state ->
            state.copy(pendingRequests = state.pendingRequests.filterNot { it.id == request.id })
        }
    }
}
