package com.kuit.youthroulette.data

import com.kuit.youthroulette.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object UserRepository {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    fun saveUser(
        id: String,
        nickname: String,
        profileImageUrl: String? = null
    ) {
        _currentUser.value = User(
            id = id,
            nickname = nickname,
            profileImageUrl = profileImageUrl
        )
    }

    fun getCurrentUser(): User? {
        return _currentUser.value
    }
}