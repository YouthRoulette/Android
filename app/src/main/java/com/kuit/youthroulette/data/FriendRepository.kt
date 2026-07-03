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

    private val api = ApiClient.api

    suspend fun getFriends(): Result<List<FriendUiModel>> = runCatching {
        api.getFriends(SessionManager.bearerToken()).map { it.toFriendUiModel() }
    }

    suspend fun getReceivedRequests(): Result<List<FriendRequestUiModel>> = runCatching {
        api.getReceivedRequests(SessionManager.bearerToken()).map { it.toFriendRequestUiModel() }
    }

    suspend fun getFeed(): Result<List<FeedUiModel>> = runCatching {
        api.getFeed(SessionManager.bearerToken()).map { it.toFeedUiModel() }
    }

    suspend fun sendRequest(loginId: String): Result<Unit> = runCatching {
        api.sendFriendRequest(SessionManager.bearerToken(), FriendRequestCreate(loginId))
        Unit
    }

    suspend fun acceptRequest(friendId: Int): Result<Unit> = runCatching {
        api.acceptFriendRequest(SessionManager.bearerToken(), friendId)
        Unit
    }

    suspend fun rejectRequest(friendId: Int): Result<Unit> = runCatching {
        api.rejectFriendRequest(SessionManager.bearerToken(), friendId)
        Unit
    }

    suspend fun likePost(postId: Int): Result<Boolean> = runCatching {
        api.likePost(SessionManager.bearerToken(), postId).likedByMe
    }

    suspend fun unlikePost(postId: Int): Result<Boolean> = runCatching {
        api.unlikePost(SessionManager.bearerToken(), postId).likedByMe
    }
}
