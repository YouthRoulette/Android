package com.kuit.youthroulette.ui.friend

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FriendViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        FriendUiState(
            friends = listOf(
                FriendUiModel(id = 1, name = "민수", avatarEmoji = "🙂"),
                FriendUiModel(id = 2, name = "지연", avatarEmoji = "🙂"),
                FriendUiModel(id = 3, name = "영희", avatarEmoji = "🙂"),
                FriendUiModel(id = 4, name = "한우", avatarEmoji = "🙂")
            ),
            feeds = listOf(
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
                    avatarEmoji = "🙂",
                    message = "'맛집 탐방하기' 완료! 맛있는 거 많이 먹었다 🍜",
                    time = "5시간 전",
                    likeCount = 31,
                    bucketEmoji = "🍜"
                ),
                FeedUiModel(
                    id = 3,
                    name = "영희",
                    avatarEmoji = "🙂",
                    message = "'야시장 가기' 완료! 사람도 많고 재밌었어 ✨",
                    time = "1일 전",
                    likeCount = 18,
                    bucketEmoji = "🏮"
                )
            ),
            pendingRequests = listOf(
                FriendRequestUiModel(id = "req1", name = "수아", userId = "sua_youth")
            )
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
