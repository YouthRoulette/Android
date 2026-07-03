package com.kuit.youthroulette.ui.proof

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.youthroulette.data.FriendRepository
import com.kuit.youthroulette.data.ImageRepository
import com.kuit.youthroulette.data.ResultRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProofViewModel : ViewModel() {

    private val friendRepository = FriendRepository
    private val resultRepository = ResultRepository()
    private val imageRepository = ImageRepository()

    private val _uiState = MutableStateFlow(ProofUiState())
    val uiState: StateFlow<ProofUiState> = _uiState.asStateFlow()

    init {
        loadFriends()
    }

    private fun loadFriends() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {
                val friends = FriendRepository.getFriendsForProof()
                    .getOrElse { emptyList() }

                _uiState.update {
                    it.copy(
                        friends = friends,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
            }
        }
    }

    fun updateContent(content: String) {
        _uiState.update {
            it.copy(content = content)
        }
    }

    fun updatePublic(isPublic: Boolean) {
        _uiState.update {
            it.copy(isPublic = isPublic)
        }
    }

    fun updateFriendTagText(text: String) {
        _uiState.update {
            it.copy(friendTagText = text)
        }
    }

    fun updateSelectedImage(uri: Uri?) {
        _uiState.update {
            it.copy(selectedImageUri = uri)
        }
    }

    fun toggleFriendSelection(friendId: Int) {
        _uiState.update { state ->
            val newSelectedFriendIds =
                if (friendId in state.selectedFriendIds) {
                    state.selectedFriendIds - friendId
                } else {
                    state.selectedFriendIds + friendId
                }

            state.copy(selectedFriendIds = newSelectedFriendIds)
        }
    }

    fun submitProof(bucketId: Int, context: Context) {
        viewModelScope.launch {
            val currentState = _uiState.value

            if (currentState.selectedImageUri == null) {
                _uiState.update {
                    it.copy(errorMessage = "인증 이미지를 선택해주세요.")
                }
                return@launch
            }

            if (currentState.content.isBlank()) {
                _uiState.update {
                    it.copy(errorMessage = "인증 내용을 입력해주세요.")
                }
                return@launch
            }

            _uiState.update {
                it.copy(
                    isSubmitting = true,
                    submitSuccess = false,
                    errorMessage = null
                )
            }

            try {
                val imageUrl = imageRepository.uploadImageToS3(
                    context = context.applicationContext,
                    imageUri = currentState.selectedImageUri
                )

                val visibility = if (currentState.isPublic) {
                    "PUBLIC"
                } else {
                    "PRIVATE"
                }

                val success = resultRepository.createPost(
                    bucketId = bucketId,
                    imageUrl = imageUrl,
                    reviewText = currentState.content,
                    visibility = visibility,
                    friendIds = currentState.selectedFriendIds
                )

                _uiState.update {
                    it.copy(
                        selectedImageUrl = imageUrl,
                        isSubmitting = false,
                        submitSuccess = success,
                        errorMessage = if (success) null else "인증 완료에 실패했습니다."
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isSubmitting = false,
                        submitSuccess = false,
                        errorMessage = e.message ?: "알 수 없는 오류가 발생했습니다."
                    )
                }
            }
        }
    }
}