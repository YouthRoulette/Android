package com.kuit.youthroulette.data.mapper
//서버 응답 DTO를 기존 앱 모델로 바꾸는 파일,기존 BucketItem, PendingBucket 생성자에 맞춰 수정해야
import com.kuit.youthroulette.data.remote.dto.BucketDto
import com.kuit.youthroulette.model.BucketItem
import com.kuit.youthroulette.model.PendingBucket

fun BucketDto.toBucketItem(): BucketItem {
    return BucketItem(
        bucketId = bucketId,
        title = title,
        isCompleted = status == "COMPLETED"
    )
}

fun BucketDto.toPendingBucket(): PendingBucket {
    return PendingBucket(
        bucketId = bucketId,
        title = title
    )
}