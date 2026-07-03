package com.kuit.youthroulette.data

import com.kuit.youthroulette.data.mapper.toFeedPost
import com.kuit.youthroulette.data.mapper.toPendingBucket
import com.kuit.youthroulette.data.remote.ApiClient
import com.kuit.youthroulette.data.remote.dto.CreatePostRequest
import com.kuit.youthroulette.model.FeedPost
import com.kuit.youthroulette.model.PendingBucket

class ResultRepository {

    private val api = ApiClient.api

    suspend fun getPendingBuckets(): List<PendingBucket> {
        val response = api.getPendingBuckets()

        if (response.isSuccessful) {
            return response.body()
                ?.map { it.toPendingBucket() }
                ?: emptyList()
        }

        return emptyList()
    }

    suspend fun getCompletedPosts(): List<FeedPost> {
        val response = api.getCompletedPosts()

        if (response.isSuccessful) {
            return response.body()
                ?.map { it.toFeedPost() }
                ?: emptyList()
        }

        return emptyList()
    }

    suspend fun createPost(
        bucketId: Int,
        imageUrl: String,
        reviewText: String,
        visibility: String,
        friendIds: List<Int>
    ): Boolean {
        val request = CreatePostRequest(
            imageUrl = imageUrl,
            reviewText = reviewText,
            visibility = visibility,
            friendIds = friendIds
        )

        val response = api.createPost(
            bucketId = bucketId,
            request = request
        )

        return response.isSuccessful
    }
}