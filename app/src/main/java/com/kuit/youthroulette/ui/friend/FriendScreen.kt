package com.kuit.youthroulette.ui.friend

import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuit.youthroulette.ui.component.CommonTopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendScreen(
    viewModel: FriendViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.refresh()
    }

    var selectedTab by rememberSaveable { mutableStateOf(FriendTab.LIST) }
    var showAddSheet by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        containerColor = ScreenBackground,
        topBar = {
            CommonTopBar(
                title = "친구",
                containerColor = ScreenBackground,
                actions = {
                    AddFriendAction(onClick = { showAddSheet = true })
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 20.dp)
        ) {
            FriendSegmentedTab(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.pendingRequests.isNotEmpty()) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    uiState.pendingRequests.forEach { request ->
                        FriendRequestBanner(
                            request = request,
                            onAccept = { req ->
                                viewModel.acceptRequest(req)
                                Toast.makeText(
                                    context,
                                    "🎉 ${req.name}님과 친구가 됐어요!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onReject = { req ->
                                viewModel.rejectRequest(req)
                                Toast.makeText(context, "친구 요청을 거절했어요.", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            when (selectedTab) {
                FriendTab.LIST -> FriendListTab(friends = uiState.friends)
                FriendTab.NEWS -> FriendNewsTab(
                    feeds = uiState.feeds,
                    onLikeClick = { feedId -> viewModel.toggleLike(feedId) }
                )
            }
        }
    }

    if (showAddSheet) {
        AddFriendSheet(
            sheetState = sheetState,
            onDismiss = {
                coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) showAddSheet = false
                }
            },
            onSend = { userId ->
                viewModel.sendRequest(userId) { success ->
                    val message = if (success) {
                        "💌 ${userId}님에게 친구 요청을 보냈어요!"
                    } else {
                        "친구 요청을 보내지 못했어요. 아이디를 확인해주세요."
                    }
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
                coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) showAddSheet = false
                }
            }
        )
    }
}

@Composable
private fun AddFriendAction(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(end = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 6.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "친구 추가",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = AddButtonColor
        )
    }
}

@Composable
private fun FriendSegmentedTab(
    selectedTab: FriendTab,
    onTabSelected: (FriendTab) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(TabBackground, RoundedCornerShape(20.dp))
            .padding(6.dp)
    ) {
        FriendTab.entries.forEach { tab ->
            val selected = tab == selectedTab
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (selected) Color.White else Color.Transparent)
                    .clickable { onTabSelected(tab) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = tab.label,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AccentOrange
                )
            }
        }
    }
}

@Composable
private fun FriendRequestBanner(
    request: FriendRequestUiModel,
    onAccept: (FriendRequestUiModel) -> Unit,
    onReject: (FriendRequestUiModel) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(RequestBannerBackground, RoundedCornerShape(16.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${request.name}님의 친구 요청 🙋",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = RequestBannerText
            )
            Text(
                text = request.userId,
                fontSize = 12.sp,
                color = MutedText
            )
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(AcceptButtonColor)
                .clickable { onAccept(request) }
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(text = "수락", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(modifier = Modifier.width(6.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .border(1.dp, CardBorderColor, RoundedCornerShape(12.dp))
                .clickable { onReject(request) }
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(text = "거절", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MutedText)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddFriendSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onSend: (userId: String) -> Unit
) {
    var inputId by remember { mutableStateOf("") }

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
            Text(text = "친구 추가", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "친구의 아이디를 입력하고 친구 요청을 보내보세요!",
                fontSize = 13.sp,
                color = MutedText
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = inputId,
                onValueChange = { inputId = it.removePrefix("@") },
                placeholder = { Text("예: youth_friend", color = Color.Gray) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            val canSend = inputId.isNotBlank()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (canSend) AddButtonColor else Color.LightGray)
                    .clickable(enabled = canSend) { onSend(inputId.trim()) }
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "친구 요청 보내기",
                    color = if (canSend) Color.White else Color.Gray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }
    }
}
