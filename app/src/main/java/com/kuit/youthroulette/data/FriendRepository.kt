package com.kuit.youthroulette.data

import com.kuit.youthroulette.data.mapper.toFeedUiModel
import com.kuit.youthroulette.data.mapper.toFriendRequestUiModel
import com.kuit.youthroulette.data.mapper.toFriendUiModel
import com.kuit.youthroulette.data.remote.ApiClient
import com.kuit.youthroulette.data.remote.dto.FriendRequestCreate
import com.kuit.youthroulette.ui.friend.FeedUiModel
import com.kuit.youthroulette.ui.friend.FriendRequestUiModel
import com.kuit.youthroulette.ui.friend.FriendUiModel

object FriendRepository {

    private val api get() = ApiClient.api

    suspend fun getFriends(): Result<List<FriendUiModel>> = runCatching {
        api.getFriends().map { it.toFriendUiModel() }
    }

    suspend fun getReceivedRequests(): Result<List<FriendRequestUiModel>> = runCatching {
        api.getReceivedRequests().map { it.toFriendRequestUiModel() }
    }

    suspend fun getFeed(): Result<List<FeedUiModel>> = runCatching {
        api.getFeed().map { it.toFeedUiModel() }
    }

    suspend fun sendRequest(loginId: String): Result<Unit> = runCatching {
        api.sendFriendRequest(FriendRequestCreate(loginId))
        Unit
    }

    suspend fun acceptRequest(friendId: Int): Result<Unit> = runCatching {
        api.acceptFriendRequest(friendId)
        Unit
    }

    suspend fun rejectRequest(friendId: Int): Result<Unit> = runCatching {
        api.rejectFriendRequest(friendId)
        Unit
    }

    suspend fun likePost(postId: Int): Result<Boolean> = runCatching {
        api.likePost(postId).likedByMe
    }

    suspend fun unlikePost(postId: Int): Result<Boolean> = runCatching {
        api.unlikePost(postId).likedByMe
    }
}
