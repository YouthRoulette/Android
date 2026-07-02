package com.kuit.youthroulette.data

import com.kuit.youthroulette.model.BucketItem
import com.kuit.youthroulette.model.BucketStatus

object MockData {

    val bucketItems: List<BucketItem> = listOf(
        BucketItem(id = 1, title = "피크닉 가기", category = "야외", emoji = "🧺", status = BucketStatus.NOT_STARTED),
        BucketItem(id = 2, title = "일출 보기", category = "여행", emoji = "🌅", status = BucketStatus.IN_PROGRESS),
        BucketItem(id = 3, title = "캠핑 가기", category = "야외", emoji = "⛺", status = BucketStatus.NOT_STARTED),
        BucketItem(id = 4, title = "한강에서 치맥하기", category = "일상", emoji = "🍗", status = BucketStatus.NOT_STARTED),
        BucketItem(id = 5, title = "밤바다 보기", category = "여행", emoji = "🌊", status = BucketStatus.NOT_STARTED),
        BucketItem(id = 6, title = "번지점프 도전", category = "액티비티", emoji = "🪂", status = BucketStatus.NOT_STARTED),
        BucketItem(id = 7, title = "혼자 여행 가기", category = "여행", emoji = "🎒", status = BucketStatus.NOT_STARTED),
        BucketItem(id = 8, title = "오로라 보러 가기", category = "여행", emoji = "🌌", status = BucketStatus.NOT_STARTED),
    )
}
