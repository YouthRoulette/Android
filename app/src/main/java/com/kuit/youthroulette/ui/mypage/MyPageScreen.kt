package com.kuit.youthroulette.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuit.youthroulette.data.MockData
import com.kuit.youthroulette.ui.component.CommonTopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageScreen(
    viewModel: MyPageViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var showEmojiSheet by remember { mutableStateOf(false) }
    var showNicknameDialog by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        containerColor = ScreenBackground,
        topBar = {
            CommonTopBar(title = "마이페이지", containerColor = ScreenBackground)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            ProfileCard(
                profileEmoji = MockData.profileEmojiOptions[uiState.profileEmojiIndex],
                nickname = uiState.nickname,
                userId = uiState.userId,
                onProfileClick = { showEmojiSheet = true },
                onEditNickname = { showNicknameDialog = true }
            )

            Spacer(modifier = Modifier.height(48.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatCard(
                    value = uiState.challengedCount.toString(),
                    label = "도전한 버킷",
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    value = uiState.completedCount.toString(),
                    label = "완료한 버킷",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }

    if (showEmojiSheet) {
        ProfileEmojiSheet(
            sheetState = sheetState,
            selectedIndex = uiState.profileEmojiIndex,
            emojiOptions = MockData.profileEmojiOptions,
            onSelect = { index ->
                viewModel.changeProfileEmoji(index)
                coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) showEmojiSheet = false
                }
            },
            onDismiss = {
                coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) showEmojiSheet = false
                }
            }
        )
    }

    if (showNicknameDialog) {
        NicknameEditDialog(
            currentNickname = uiState.nickname,
            onConfirm = { newNickname ->
                viewModel.changeNickname(newNickname)
                showNicknameDialog = false
            },
            onDismiss = { showNicknameDialog = false }
        )
    }
}

@Composable
private fun StatCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(CardBackground)
            .border(1.dp, CardBorderColor, RoundedCornerShape(18.dp))
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = StatValueColor
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = label, fontSize = 13.sp, color = MutedText)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileEmojiSheet(
    sheetState: SheetState,
    selectedIndex: Int,
    emojiOptions: List<String>,
    onSelect: (Int) -> Unit,
    onDismiss: () -> Unit
) {
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
            Text(text = "프로필 이모지 선택", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(20.dp))

            val rows = emojiOptions.chunked(4)
            rows.forEachIndexed { rowIndex, rowEmojis ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowEmojis.forEachIndexed { columnIndex, emoji ->
                        val index = rowIndex * 4 + columnIndex
                        val selected = index == selectedIndex
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(if (selected) AvatarBackground else EmojiSlotBackground)
                                .border(
                                    width = if (selected) 2.dp else 0.dp,
                                    color = if (selected) SelectedBorderColor else Color.Transparent,
                                    shape = CircleShape
                                )
                                .clickable { onSelect(index) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = emoji, fontSize = 26.sp)
                        }
                    }
                }
                if (rowIndex != rows.lastIndex) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
private fun NicknameEditDialog(
    currentNickname: String,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var input by remember { mutableStateOf(currentNickname) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "닉네임 변경") },
        text = {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                placeholder = { Text("새로운 닉네임을 입력해주세요") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(input.trim()) },
                enabled = input.isNotBlank()
            ) {
                Text(text = "변경", color = AccentOrange, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "취소", color = MutedText)
            }
        }
    )
}
