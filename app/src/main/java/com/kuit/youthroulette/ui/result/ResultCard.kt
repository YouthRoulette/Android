package com.kuit.youthroulette.ui.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

private val bucketEmojis = listOf(
    "🧺", "🌅", "⛺", "🍗", "🌊", "🪂", "🎒", "🌌", "⛰", "✨", "🎸"
)

private fun bucketEmojiFor(index: Int): String {
    val safeIndex = ((index % bucketEmojis.size) + bucketEmojis.size) % bucketEmojis.size
    return bucketEmojis[safeIndex]
}

@Composable
fun ResultCard(
    result: ResultItemUiModel,
    onProofClick: () -> Unit = {}
) {
    if (result.isCompleted) {
        CompletedResultCard(result = result)
    } else {
        PendingResultCard(
            result = result,
            onProofClick = onProofClick
        )
    }
}

@Composable
private fun PendingResultCard(
    result: ResultItemUiModel,
    onProofClick: () -> Unit
) {
    val emoji = bucketEmojiFor(result.emojiIndex)

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFDDE6FA)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = emoji,
                    fontSize = 34.sp
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = result.title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2B2B2B)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = onProofClick,
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4D65A1),
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(
                        horizontal = 24.dp,
                        vertical = 10.dp
                    )
                ) {
                    Text(
                        text = "인증하기",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun CompletedResultCard(
    result: ResultItemUiModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = result.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "인증 완료",
                modifier = Modifier.padding(top = 8.dp)
            )

            if (result.content.isNotBlank()) {
                Text(
                    text = "인증 내용: ${result.content}",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            result.proofImageUrl?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "인증 사진",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(top = 8.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}