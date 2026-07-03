package com.kuit.youthroulette.ui.roulette

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuit.youthroulette.model.BucketItem
import com.kuit.youthroulette.ui.bucket.EmojiOptions
import com.kuit.youthroulette.ui.bucket.IconBackgroundPalette

private val AnnouncementColor = Color(0xFFEE8A3D)
private val CardBorderColor = Color(0xFFF0EDE8)
private val QuoteColor = Color(0xFF9C9C9C)
private val CategoryChipBackground = Color(0xFFD9F0E1)
private val CategoryChipTextColor = Color(0xFF3E8A62)
private val PrimaryButtonColor = Color(0xFFF0A868)
private val SecondaryButtonBorderColor = Color(0xFFF0A868)
private val CardShape = RoundedCornerShape(24.dp)

@Composable
fun RouletteResultCard(
    bucket: BucketItem,
    onStartChallenge: () -> Unit,
    onRespin: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "🎉 오늘의 버킷이 선정됐어요! 🎉",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = AnnouncementColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(CardShape)
                .background(Color.White)
                .border(1.dp, CardBorderColor, CardShape)
                .padding(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(IconBackgroundPalette[bucket.colorIndex % IconBackgroundPalette.size]),
                contentAlignment = Alignment.Center
            ) {
                Text(text = EmojiOptions[bucket.emojiIndex % EmojiOptions.size], fontSize = 56.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = bucket.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(PrimaryButtonColor)
                .clickable(onClick = onStartChallenge)
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "도전 시작하기", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, SecondaryButtonBorderColor, RoundedCornerShape(16.dp))
                .clickable(onClick = onRespin)
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "다시 돌리기", color = PrimaryButtonColor, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun RouletteResultCardPreview() {
    Column(
        modifier = Modifier.padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        RouletteResultCard(
            bucket = BucketItem(id = 4, title = "밤바다 보기", emojiIndex = 4, colorIndex = 5),
            onStartChallenge = {},
            onRespin = {}
        )
    }
}
