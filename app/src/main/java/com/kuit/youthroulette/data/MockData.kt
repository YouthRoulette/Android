package com.kuit.youthroulette.data

import com.kuit.youthroulette.model.BucketItem
import com.kuit.youthroulette.model.BucketStatus

object MockData {

    val bucketItems: List<BucketItem> = listOf(
        BucketItem(id = 1, title = "피크닉 가기", category = "야외", emojiIndex = 0, status = BucketStatus.NOT_STARTED, colorIndex = 1),
        BucketItem(id = 2, title = "일출 보기", category = "여행", emojiIndex = 1, status = BucketStatus.IN_PROGRESS, colorIndex = 2),
        BucketItem(id = 3, title = "캠핑 가기", category = "야외", emojiIndex = 2, status = BucketStatus.NOT_STARTED, colorIndex = 3),
        BucketItem(id = 4, title = "한강에서 치맥하기", category = "일상", emojiIndex = 3, status = BucketStatus.NOT_STARTED, colorIndex = 4),
        BucketItem(id = 5, title = "밤바다 보기", category = "여행", emojiIndex = 4, status = BucketStatus.NOT_STARTED, colorIndex = 5),
        BucketItem(id = 6, title = "번지점프 도전", category = "액티비티", emojiIndex = 5, status = BucketStatus.NOT_STARTED, colorIndex = 6),
        BucketItem(id = 7, title = "혼자 여행 가기", category = "여행", emojiIndex = 6, status = BucketStatus.NOT_STARTED, colorIndex = 7),
        BucketItem(id = 8, title = "오로라 보러 가기", category = "여행", emojiIndex = 7, status = BucketStatus.NOT_STARTED, colorIndex = 0)
    )
}
