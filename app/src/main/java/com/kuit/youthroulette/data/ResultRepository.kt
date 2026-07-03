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
        return api.getPendingBuckets()
            .map { it.toPendingBucket() }
    }

    suspend fun getCompletedPosts(): List<FeedPost> {
        return api.getCompletedPosts()
            .map { it.toFeedPost() }
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
            friendIds = friendIds.map { it.toLong() }
        )

        api.createPost(
            bucketId = bucketId,
            request = request
        )

        return true
    }
}