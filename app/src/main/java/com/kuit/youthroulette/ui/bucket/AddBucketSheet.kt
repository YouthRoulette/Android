package com.kuit.youthroulette.ui.bucket

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 이모지 배경 색상
private val EmojiSlotBackground = Color(0xFFF4F2EF)
// 이모지 선택시 테투리 색상
private val SelectedBorderColor = Color(0xFF2B2B2B)
// 버킷 이름 최대 글자 수(공백 포함)
private const val TITLE_MAX_LENGTH = 8

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBucketSheet(
    sheetState: SheetState,
    iconColorPalette: List<Color>,
    emojiOptions: List<String>,
    onDismiss: () -> Unit,
    onAdd: (title: String, content: String, category: String, emojiIndex: Int, colorIndex: Int) -> Unit
) {
    // 버킷리스트 제목
    var title by remember { mutableStateOf("") }
    // 버킷리스트 내용
    var content by remember { mutableStateOf("") }
    // 버킷리스트 카테고리
    var category by remember { mutableStateOf("") }
    // 선택한 이모지 위치
    var selectedEmojiIndex by remember { mutableIntStateOf(0) }
    // 선택한 배경 색상 위치
    var selectedColorIndex by remember { mutableIntStateOf(0) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = "버킷 추가하기",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "이름", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = title,
                onValueChange = { input -> title = input.take(TITLE_MAX_LENGTH) },
                placeholder = { Text("버킷 이름을 입력해주세요") },
                singleLine = true,
                supportingText = { Text("${title.length}/$TITLE_MAX_LENGTH") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "내용", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                placeholder = { Text("버킷에 대한 설명을 입력해주세요 (선택)") },
                minLines = 2,
                maxLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "카테고리", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                placeholder = { Text("예: 여행 · 힐링 (선택)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "이모지 선택", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                emojiOptions.forEachIndexed { index, emoji ->
                    val selected = index == selectedEmojiIndex
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(if (selected) iconColorPalette[selectedColorIndex] else EmojiSlotBackground)
                            .border(
                                width = if (selected) 2.dp else 0.dp,
                                color = if (selected) SelectedBorderColor else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable { selectedEmojiIndex = index },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = emoji, fontSize = 20.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "색상 선택", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                iconColorPalette.forEachIndexed { index, color ->
                    val selected = index == selectedColorIndex
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = if (selected) 2.dp else 0.dp,
                                color = SelectedBorderColor,
                                shape = CircleShape
                            )
                            .clickable { selectedColorIndex = index }
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            val canSubmit = title.isNotBlank()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (canSubmit) iconColorPalette[selectedColorIndex] else Color.LightGray)
                    .clickable(enabled = canSubmit) {
                        onAdd(title.trim(), content.trim(), category.trim(), selectedEmojiIndex, selectedColorIndex)
                    }
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "추가하기",
                    color = if (canSubmit) SelectedBorderColor else Color.Gray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }
    }
}