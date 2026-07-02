package com.kuit.youthroulette.ui.proof

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VisibilitySelector(
    isPublic: Boolean,
    onPublicSelected: () -> Unit,
    onPrivateSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        VisibilityOptionCard(
            title = "공개",
            description = "커뮤니티에 공유하고 친구들과 함께해요!",
            selected = isPublic,
            onClick = onPublicSelected
        )

        Spacer(modifier = Modifier.height(12.dp))

        VisibilityOptionCard(
            title = "비공개",
            description = "나만의 기록으로 간직할게요!",
            selected = !isPublic,
            onClick = onPrivateSelected
        )
    }
}

@Composable
private fun VisibilityOptionCard(
    title: String,
    description: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(92.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                Color(0xFFFFF9F4)
            } else {
                Color.White
            }
        ),
        border = if (selected) {
            BorderStroke(
                width = 2.dp,
                color = Color(0xFFF0B28A)
            )
        } else {
            BorderStroke(
                width = 1.dp,
                color = Color(0xFFF5E5D8)
            )
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color(0xFFF0B28A),
                    unselectedColor = Color(0xFFF0D9C6)
                )
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2B2724)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color(0xFF9A8D84)
                )
            }
        }
    }
}