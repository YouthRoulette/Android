package com.kuit.youthroulette.data

import androidx.compose.runtime.mutableStateListOf
import com.kuit.youthroulette.model.BucketItem
import com.kuit.youthroulette.model.BucketStatus

object BucketRepository {

    val bucketItems = mutableStateListOf<BucketItem>().apply { addAll(MockData.bucketItems) }

    fun add(item: BucketItem) {
        bucketItems.add(item)
    }

    fun delete(id: Int) {
        bucketItems.removeAll { it.id == id }
    }

    fun complete(id: Int) {
        updateStatus(id, BucketStatus.COMPLETED)
    }

    fun startChallenge(id: Int) {
        updateStatus(id, BucketStatus.IN_PROGRESS)
    }

    private fun updateStatus(id: Int, status: BucketStatus) {
        val index = bucketItems.indexOfFirst { it.id == id }
        if (index != -1) {
            bucketItems[index] = bucketItems[index].copy(status = status)
        }
    }
}
