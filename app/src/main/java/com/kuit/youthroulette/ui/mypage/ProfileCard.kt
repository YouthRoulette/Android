package com.kuit.youthroulette.ui.mypage

import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileCard(
    profileEmoji: String,
    nickname: String,
    userId: String,
    onProfileClick: () -> Unit,
    onEditNickname: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 프로필 사진 (이모지) - 눌러서 변경
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(AvatarBackground)
                .clickable(onClick = onProfileClick),
            contentAlignment = Alignment.Center
        ) {
            Text(text = profileEmoji, fontSize = 56.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "이모지를 눌러 프로필을 바꿔보세요",
            fontSize = 12.sp,
            color = MutedText
        )

        Spacer(modifier = Modifier.height(36.dp))

        // 닉네임 + 닉네임 변경(연필) 메뉴
        Row(verticalAlignment = Alignment.CenterVertically) {
            // 닉네임 글자 왼쪽에 닉네임 변경 메뉴 (연필 이모지)
            Text(
                text = "✏️",
                fontSize = 18.sp,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = onEditNickname)
                    .padding(6.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = nickname,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 아이디는 @ 없이 표시
        Text(
            text = userId,
            fontSize = 14.sp,
            color = MutedText
        )
    }
}
