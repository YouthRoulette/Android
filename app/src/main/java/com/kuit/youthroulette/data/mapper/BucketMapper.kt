package com.kuit.youthroulette.data.mapper
//서버 응답 DTO를 기존 앱 모델로 바꾸는 파일,기존 BucketItem, PendingBucket 생성자에 맞춰 수정해야
import com.kuit.youthroulette.data.remote.dto.BucketDto
import com.kuit.youthroulette.model.BucketItem
import com.kuit.youthroulette.model.BucketStatus
import com.kuit.youthroulette.model.PendingBucket

fun BucketDto.toBucketItem(): BucketItem {
    return BucketItem(
        id = bucketId,
        title = title,
        status = status.toBucketStatus(),
        emojiIndex = emojiIndex,
        colorIndex = colorIndex
    )
}

fun BucketDto.toPendingBucket(): PendingBucket {
    return PendingBucket(
        id = bucketId,
        bucketId = bucketId,
        title = title,
        date = startedAt ?: createdAt.orEmpty(),
        content = "",
        taggedFriendNames = emptyList(),
        category = "",
        emojiIndex = emojiIndex
    )
}

private fun String?.toBucketStatus(): BucketStatus {
    return when (this) {
        "COMPLETED" -> BucketStatus.COMPLETED
        "IN_PROGRESS" -> BucketStatus.IN_PROGRESS
        "NOT_STARTED" -> BucketStatus.NOT_STARTED
        else -> BucketStatus.NOT_STARTED
    }
}
