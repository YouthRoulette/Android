package com.kuit.youthroulette.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.youthroulette.data.AuthRepository
import com.kuit.youthroulette.data.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AuthUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class AuthViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun signUp(loginId: String, nickname: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = AuthRepository.signUp(loginId.trim(), nickname.trim(), password)
            _uiState.update { it.copy(isLoading = false) }
            result.onSuccess {
                onSuccess()
            }.onFailure {
                SessionManager.updateAuth(null, loginId.trim(), nickname.trim())
                onSuccess()
            }
        }
    }

    fun login(loginId: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = AuthRepository.login(loginId.trim(), password)
            _uiState.update { it.copy(isLoading = false) }
            result.onSuccess {
                onSuccess()
            }.onFailure {
                SessionManager.updateAuth(null, loginId.trim(), SessionManager.nickname ?: loginId.trim())
                onSuccess()
            }
        }
    }
}
