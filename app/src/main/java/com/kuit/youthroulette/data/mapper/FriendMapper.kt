package com.kuit.youthroulette.data.mapper
//FriendDto를 기존 Friend 모델로 바꾸는 파일, 기존 Friend 모델 생성자에 맞춰 수정
import com.kuit.youthroulette.data.remote.dto.FriendDto
import com.kuit.youthroulette.model.Friend

fun FriendDto.toFriend(): Friend {
    return Friend(
        friendId = friendId,
        nickname = nickname,
        profileImageUrl = profileImageUrl
    )
}
