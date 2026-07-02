package com.kuit.youthroulette.ui.roulette

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuit.youthroulette.data.BucketRepository
import com.kuit.youthroulette.model.BucketStatus
import com.kuit.youthroulette.ui.component.CommonTopBar

private val SubtitleColor = Color(0xFF9C9C9C)
private val BannerBackground = Color(0xFFFCEFAE)
private val BannerTextColor = Color(0xFF7A6A2E)

@Composable
fun RouletteScreen() {
    // 룰렛에는 미 완료 상태인 버킷만 들어간다. BucketRepository.bucketItems를 읽는 순간
    // 스냅샷 상태가 구독되므로, 다른 화면에서 상태를 바꾸면 여기도 자동으로 재구성된다.
    val rouletteItems = BucketRepository.bucketItems.filter { it.status == BucketStatus.NOT_STARTED }

    Scaffold(
        topBar = { CommonTopBar(title = "청춘룰렛") }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "오늘의 버킷을\n돌려볼까요?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "룰렛을 돌려 오늘의 도전을 정해보세요!",
                fontSize = 15.sp,
                color = SubtitleColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            if (rouletteItems.isEmpty()) {
                Text(
                    text = "미 완료 버킷을 추가하면\n룰렛이 채워져요!",
                    fontSize = 14.sp,
                    color = SubtitleColor,
                    textAlign = TextAlign.Center
                )
            } else {
                RouletteWheel(
                    items = rouletteItems,
                    modifier = Modifier.fillMaxWidth(0.85f)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BannerBackground, RoundedCornerShape(20.dp))
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🎡 룰렛은 내가 추가한 버킷리스트로 구성돼요!",
                    color = BannerTextColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
@Preview
fun RouletteScreenPreview() {
    RouletteScreen()
}
