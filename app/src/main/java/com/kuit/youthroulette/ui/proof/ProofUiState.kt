package com.kuit.youthroulette.ui.proof

import android.net.Uri
import com.kuit.youthroulette.model.Friend

data class ProofUiState(
    val content: String = "",
    val isPublic: Boolean = true,
    val friendTagText: String = "",

    val friends: List<Friend> = emptyList(),
    val selectedFriendIds: List<Int> = emptyList(),

    val selectedImageUri: Uri? = null,
    val selectedImageUrl: String? = null,

    val isLoading: Boolean = false,
    val isSubmitting: Boolean = false,
    val submitSuccess: Boolean = false,
    val errorMessage: String? = null
)