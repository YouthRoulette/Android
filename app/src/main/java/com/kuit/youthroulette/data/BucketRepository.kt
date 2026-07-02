package com.kuit.youthroulette.data

import androidx.compose.runtime.mutableStateListOf
import com.kuit.youthroulette.model.BucketItem
import com.kuit.youthroulette.model.BucketStatus

object BucketRepository {

    val bucketItems = mutableStateListOf<BucketItem>().apply { addAll(MockData.bucketItems) }

    fun add(item: BucketItem) {
        bucketItems.add(item)
    }

    fun complete(id: Int) {
        val index = bucketItems.indexOfFirst { it.id == id }
        if (index != -1) {
            bucketItems[index] = bucketItems[index].copy(status = BucketStatus.COMPLETED)
        }
    }
}
