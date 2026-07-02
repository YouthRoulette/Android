package com.kuit.youthroulette.data

import com.kuit.youthroulette.model.BucketItem
import com.kuit.youthroulette.model.BucketStatus

object MockData {

    val bucketItems: List<BucketItem> = listOf(
        BucketItem(id = 1, title = "피크닉 가기", content = "포근한 돗자리 위에서 여유로운 오후를", category = "여유 · 힐링", emojiIndex = 0, status = BucketStatus.NOT_STARTED, colorIndex = 1),
        BucketItem(id = 2, title = "일출 보기", content = "붉게 물든 하늘 아래 새로운 다짐을", category = "여행 · 감성", emojiIndex = 1, status = BucketStatus.IN_PROGRESS, colorIndex = 2),
        BucketItem(id = 3, title = "캠핑 가기", content = "모닥불 앞에서 별을 세는 밤", category = "야외 · 힐링", emojiIndex = 2, status = BucketStatus.NOT_STARTED, colorIndex = 3),
        BucketItem(id = 4, title = "한강에서 치맥하기", content = "선선한 바람 맞으며 치킨에 맥주 한 잔", category = "여유 · 힐링", emojiIndex = 3, status = BucketStatus.NOT_STARTED, colorIndex = 4),
        BucketItem(id = 5, title = "밤바다 보기", content = "파도 소리를 들으며 마음을 정리해요", category = "여행 · 감성", emojiIndex = 4, status = BucketStatus.NOT_STARTED, colorIndex = 5),
        BucketItem(id = 6, title = "번지점프 도전", content = "심장이 쫄깃해지는 짜릿한 순간", category = "액티비티 · 도전", emojiIndex = 5, status = BucketStatus.NOT_STARTED, colorIndex = 6),
        BucketItem(id = 7, title = "혼자 여행 가기", content = "온전히 나에게 집중하는 시간", category = "여행 · 자유", emojiIndex = 6, status = BucketStatus.NOT_STARTED, colorIndex = 7),
        BucketItem(id = 8, title = "오로라 보러 가기", content = "평생 잊지 못할 하늘의 색을 눈에 담아요", category = "여행 · 감성", emojiIndex = 7, status = BucketStatus.NOT_STARTED, colorIndex = 0)
    )
}
