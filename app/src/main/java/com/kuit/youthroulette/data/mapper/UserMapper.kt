package com.kuit.youthroulette.data.mapper

import com.kuit.youthroulette.data.remote.dto.UserDto
import com.kuit.youthroulette.model.User

fun UserDto.toUser(): User {
    return User(
        userId = userId,
        loginId = loginId,
        nickname = nickname,
        emojiIndex = emojiIndex,
        colorIndex = colorIndex,
        challengedCount = challengedCount,
        completedCount = completedCount
    )
}
