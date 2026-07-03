package com.kuit.youthroulette.ui.friend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.youthroulette.data.FriendRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FriendViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FriendUiState())
    val uiState: StateFlow<FriendUiState> = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            FriendRepository.getFriends().onSuccess { friends ->
                _uiState.update { it.copy(friends = friends) }
            }
            FriendRepository.getReceivedRequests().onSuccess { requests ->
                _uiState.update { it.copy(pendingRequests = requests) }
            }
            FriendRepository.getFeed().onSuccess { feeds ->
                _uiState.update { it.copy(feeds = feeds) }
            }
        }
    }

    fun sendRequest(loginId: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = FriendRepository.sendRequest(loginId)
            onResult(result.isSuccess)
        }
    }

    fun acceptRequest(request: FriendRequestUiModel) {
        viewModelScope.launch {
            FriendRepository.acceptRequest(request.id)
            _uiState.update { state ->
                state.copy(
                    pendingRequests = state.pendingRequests.filterNot { it.id == request.id },
                    friends = listOf(
                        FriendUiModel(id = request.id, name = request.name, avatarEmoji = "🙂")
                    ) + state.friends
                )
            }
        }
    }

    fun rejectRequest(request: FriendRequestUiModel) {
        viewModelScope.launch {
            FriendRepository.rejectRequest(request.id)
            _uiState.update { state ->
                state.copy(pendingRequests = state.pendingRequests.filterNot { it.id == request.id })
            }
        }
    }

    fun toggleLike(feedId: Int) {
        val target = _uiState.value.feeds.firstOrNull { it.id == feedId } ?: return
        val willLike = !target.isLiked
        _uiState.update { state ->
            state.copy(
                feeds = state.feeds.map { feed ->
                    if (feed.id != feedId) {
                        feed
                    } else {
                        feed.copy(
                            isLiked = willLike,
                            likeCount = if (willLike) feed.likeCount + 1 else feed.likeCount - 1
                        )
                    }
                }
            )
        }
        viewModelScope.launch {
            if (willLike) {
                FriendRepository.likePost(feedId)
            } else {
                FriendRepository.unlikePost(feedId)
            }
        }
    }
}
