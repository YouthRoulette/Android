package com.kuit.youthroulette.ui.friend

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun FriendFeedCard(
    feed: FeedUiModel,
    onLikeClick: () -> Unit
) {
    val thumbColor = FeedThumbPalette[(feed.id - 1).coerceAtLeast(0) % FeedThumbPalette.size]

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(CardBackground)
            .border(1.dp, CardBorderColor, RoundedCornerShape(18.dp))
            .padding(14.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(AvatarBackground, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = feed.avatarEmoji, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = feed.name, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "· ${feed.time}", fontSize = 11.sp, color = MutedText)
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = feed.message, fontSize = 13.sp)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(onClick = onLikeClick)
                    .padding(vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = if (feed.isLiked) "🧡" else "🤍", fontSize = 14.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${feed.likeCount}",
                    fontSize = 12.sp,
                    color = if (feed.isLiked) AccentOrange else MutedText
                )
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(thumbColor),
            contentAlignment = Alignment.Center
        ) {
            if (feed.imageUrl != null) {
                AsyncImage(
                    model = feed.imageUrl,
                    contentDescription = "${feed.name}의 인증 사진",
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(text = feed.bucketEmoji, fontSize = 24.sp)
            }
        }
    }
}
