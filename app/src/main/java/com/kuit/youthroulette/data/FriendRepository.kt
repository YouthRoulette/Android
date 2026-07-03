package com.kuit.youthroulette.data

import com.kuit.youthroulette.data.mapper.toFriend
import com.kuit.youthroulette.data.remote.ApiClient
import com.kuit.youthroulette.model.Friend

class FriendRepository {

    private val api = ApiClient.api

    suspend fun getFriends(): List<Friend> {
        val response = api.getFriends()

        if (response.isSuccessful) {
            return response.body()
                ?.map { it.toFriend() }
                ?: emptyList()
        }

        return emptyList()
    }
}