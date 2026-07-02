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
            // 소식 리스트: 우측 썸네일은 각 친구가 완료한 버킷의 이모지로 표시
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

    // 친구 요청 수락 -> 요청 제거 + 친구 목록에 추가
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

    // 친구 요청 거절 -> 요청만 제거
    fun rejectRequest(request: FriendRequestUiModel) {
        _uiState.update { state ->
            state.copy(pendingRequests = state.pendingRequests.filterNot { it.id == request.id })
        }
    }
}
