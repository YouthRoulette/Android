package com.kuit.youthroulette.data.mapper

import com.kuit.youthroulette.data.remote.dto.BucketDto
import com.kuit.youthroulette.model.BucketItem
import com.kuit.youthroulette.model.BucketStatus
import com.kuit.youthroulette.model.PendingBucket

private fun String?.toBucketStatus(): BucketStatus = when (this) {
    "IN_PROGRESS" -> BucketStatus.IN_PROGRESS
    "COMPLETED" -> BucketStatus.COMPLETED
    else -> BucketStatus.NOT_STARTED
}

fun BucketDto.toBucketItem(): BucketItem {
    return BucketItem(
        id = bucketId,
        title = title,
        status = status.toBucketStatus()
    )
}

fun BucketDto.toPendingBucket(): PendingBucket {
    return PendingBucket(
        id = bucketId,
        bucketId = bucketId,
        title = title,
        date = "",
        content = ""
    )
}
