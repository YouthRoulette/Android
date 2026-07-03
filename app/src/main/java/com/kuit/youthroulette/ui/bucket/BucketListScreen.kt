package com.kuit.youthroulette.ui.bucket

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuit.youthroulette.data.BucketRepository
import com.kuit.youthroulette.model.BucketItem
import com.kuit.youthroulette.model.BucketStatus
import com.kuit.youthroulette.ui.component.CommonTopBar
import kotlinx.coroutines.launch

// 미 완료 버킷 배너 색상(배경, 글씨, 갯수)
private val BannerBackground = Color(0xFFFDEEE3)
private val BannerTextColor = Color(0xFF8A5A34)
private val BannerHighlightColor = Color(0xFFEE8A3D)

// 카드 테두리 색상
private val CardBorderColor = Color(0xFFF0EDE8)
// 도전 중 카드 색상(배경, 테두리)
private val InProgressBackground = Color(0xFFF3E7DC)
private val InProgressBorderColor = Color(0xFFDCC0A4)
// 완료 카드 색상(배경, 테두리)
private val CompletedBackground = Color(0xFFDFF5E3)
private val CompletedBorderColor = Color(0xFFBBE6C6)

// 도전 중 색상(배경, 글씨)
private val InProgressChipBackground = Color(0xFFE4CBAE)
private val InProgressChipTextColor = Color(0xFF7A4B24)
// 완료 색상(배경, 글씨)
private val CompletedChipBackground = Color(0xFFBFEACB)
private val CompletedChipTextColor = Color(0xFF25793D)

// 스와이프 삭제 배경 색상(배경, 글씨)
private val DeleteBackground = Color(0xFFF7C9C2)
private val DeleteTextColor = Color(0xFFB33B2E)

// 버킷리스트 아이콘 색상(인덱스로 불러옴)
val IconBackgroundPalette = listOf(
    Color(0xFFD6D2F0),
    Color(0xFFF6D9C4),
    Color(0xFFF2C79A),
    Color(0xFFF5E1A0),
    Color(0xFFB7DFD1),
    Color(0xFFBFD9F2),
    Color(0xFFEBC6C6),
    Color(0xFFD7E8B8)
)

// 버킷리스트 아이콘 이모지(인덱스로 불러옴)
val EmojiOptions = listOf(
    "🧺", "🌅", "⛺", "🍗", "🌊", "🪂", "🎒", "🌌"
)
// 최대 가능한 미 완료 버킷리스트 갯수
private const val ROULETTE_SLOT_CAPACITY = 8

// 미 완료/도전 중/완료
private enum class BucketFilter(val label: String) {
    NOT_STARTED("미 완료"),
    IN_PROGRESS("도전 중"),
    COMPLETED("완료")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BucketListScreen() {
    // 버킷 리스트 아이템 목록 (여러 화면이 공유하는 단일 소스)
    val bucketItems = BucketRepository.bucketItems
    // 선택된 탭
    var selectedFilter by remember { mutableStateOf(BucketFilter.NOT_STARTED) }
    // 버킷리스트 추가 sheet 실행 여부
    var showAddSheet by remember { mutableStateOf(false) }
    // 추가 sheet 상태
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    // 미 완료 갯수
    val notStartedCount = bucketItems.count { it.status == BucketStatus.NOT_STARTED }
    // 도전 중 갯수
    val inProgressCount = bucketItems.count { it.status == BucketStatus.IN_PROGRESS }
    // 완료 갯수
    val completedCount = bucketItems.count { it.status == BucketStatus.COMPLETED }
    // 남은 미 완료 갯수가 max값을 넘어서는지 아닌지
    val isNotStartedFull = notStartedCount >= ROULETTE_SLOT_CAPACITY
    // 선택된 탭의 아이템들
    val filteredItems = when (selectedFilter) {
        BucketFilter.NOT_STARTED -> bucketItems.filter { it.status == BucketStatus.NOT_STARTED }
        BucketFilter.IN_PROGRESS -> bucketItems.filter { it.status == BucketStatus.IN_PROGRESS }
        BucketFilter.COMPLETED -> bucketItems.filter { it.status == BucketStatus.COMPLETED }
    }

    Scaffold(
        topBar = {
            CommonTopBar(
                title = "버킷리스트",
                actions = {
                    // 버킷리스트 추가 버튼
                    AddBucketAction(
                        enabled = !isNotStartedFull,
                        onClick = { showAddSheet = true }
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {
            // 미 완료 버킷을 알려주는 배너
            NotStartedBanner(notStartedCount = notStartedCount)

            Spacer(modifier = Modifier.height(16.dp))

            // 버킷리스트 필터 탭
            BucketFilterTabs(
                selectedFilter = selectedFilter,
                notStartedCount = notStartedCount,
                inProgressCount = inProgressCount,
                completedCount = completedCount,
                onSelect = { selectedFilter = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 선택된 탭의 버킷리스트를 보여줌
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredItems, key = { it.id }) { item ->
                    // 완료된 버킷은 스와이프로 삭제할 수 없고, 미 완료/도전 중 버킷만 오른쪽으로 밀어서 삭제할 수 있다
                    if (item.status == BucketStatus.COMPLETED) {
                        BucketListItem(
                            item = item,
                            emoji = EmojiOptions[item.emojiIndex % EmojiOptions.size],
                            iconBackgroundColor = IconBackgroundPalette[item.colorIndex % IconBackgroundPalette.size],
                            onComplete = { BucketRepository.complete(item.id) }
                        )
                    } else {
                        DeletableBucketListItem(
                            item = item,
                            emoji = EmojiOptions[item.emojiIndex % EmojiOptions.size],
                            iconBackgroundColor = IconBackgroundPalette[item.colorIndex % IconBackgroundPalette.size],
                            onComplete = { BucketRepository.complete(item.id) },
                            onDelete = { BucketRepository.delete(item.id) }
                        )
                    }
                }
            }
        }
    }

    if (showAddSheet) {
        AddBucketSheet(
            sheetState = sheetState,
            iconColorPalette = IconBackgroundPalette,
            emojiOptions = EmojiOptions,
            onDismiss = {
                coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) showAddSheet = false
                }
            },
            onAdd = { title, content, category, emojiIndex, colorIndex ->
                val newId = (bucketItems.maxOfOrNull { it.id } ?: 0) + 1
                BucketRepository.add(
                    BucketItem(
                        id = newId,
                        title = title,
                        content = content,
                        category = category,
                        emojiIndex = emojiIndex,
                        colorIndex = colorIndex,
                        status = BucketStatus.NOT_STARTED
                    )
                )
                coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) showAddSheet = false
                }
            }
        )
    }
}

@Composable
private fun AddBucketAction(
    enabled: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(end = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 6.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "+ 추가",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = if (enabled) Color.Black else Color.LightGray
        )
    }
}

@Composable
private fun NotStartedBanner(notStartedCount: Int) {
    val cappedCount = notStartedCount.coerceAtMost(ROULETTE_SLOT_CAPACITY)
    val statusMessage = if (cappedCount >= ROULETTE_SLOT_CAPACITY) {
        "가득 찼어요"
    } else {
        "${ROULETTE_SLOT_CAPACITY - cappedCount}개 더 채울 수 있어요"
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(BannerBackground, RoundedCornerShape(16.dp))
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "미 완료 버킷 ",
                fontSize = 14.sp,
                color = BannerTextColor
            )
            Text(
                text = "$cappedCount / $ROULETTE_SLOT_CAPACITY",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = BannerHighlightColor
            )
            Text(
                text = " · $statusMessage",
                fontSize = 14.sp,
                color = BannerTextColor
            )
        }
    }
}

@Composable
private fun BucketFilterTabs(
    selectedFilter: BucketFilter,
    notStartedCount: Int,
    inProgressCount: Int,
    completedCount: Int,
    onSelect: (BucketFilter) -> Unit
) {
    val counts = mapOf(
        BucketFilter.NOT_STARTED to notStartedCount,
        BucketFilter.IN_PROGRESS to inProgressCount,
        BucketFilter.COMPLETED to completedCount
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray, RoundedCornerShape(16.dp))
            .padding(4.dp)
    ) {
        BucketFilter.entries.forEach { filter ->
            val selected = filter == selectedFilter
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (selected) Color.White else Color.Transparent)
                    .clickable { onSelect(filter) }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${filter.label} ${counts[filter] ?: 0}",
                    fontSize = 14.sp,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                    color = if (selected) Color.Black else Color.Gray
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DeletableBucketListItem(
    item: BucketItem,
    emoji: String,
    iconBackgroundColor: Color,
    onComplete: () -> Unit,
    onDelete: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState()

    LaunchedEffect(dismissState.currentValue) {
        if (dismissState.currentValue == SwipeToDismissBoxValue.StartToEnd) {
            onDelete()
        }
    }

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = true,
        enableDismissFromEndToStart = false,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(DeleteBackground)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(text = "삭제", color = DeleteTextColor, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
        }
    ) {
        BucketListItem(
            item = item,
            emoji = emoji,
            iconBackgroundColor = iconBackgroundColor,
            onComplete = onComplete
        )
    }
}

@Composable
private fun BucketListItem(
    item: BucketItem,
    emoji: String,
    iconBackgroundColor: Color,
    onComplete: () -> Unit
) {
    val background = when (item.status) {
        BucketStatus.NOT_STARTED -> Color.White
        BucketStatus.IN_PROGRESS -> InProgressBackground
        BucketStatus.COMPLETED -> CompletedBackground
    }
    val borderColor = when (item.status) {
        BucketStatus.NOT_STARTED -> CardBorderColor
        BucketStatus.IN_PROGRESS -> InProgressBorderColor
        BucketStatus.COMPLETED -> CompletedBorderColor
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(background)
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(iconBackgroundColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = emoji, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = item.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            textDecoration = if (item.status == BucketStatus.COMPLETED) TextDecoration.LineThrough else TextDecoration.None,
            modifier = Modifier.weight(1f)
        )

        BucketStatusChip(status = item.status, onComplete = onComplete)
    }
}

@Composable
private fun BucketStatusChip(
    status: BucketStatus,
    onComplete: () -> Unit
) {
    when (status) {
        BucketStatus.NOT_STARTED -> {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.LightGray)
                    .clickable(onClick = onComplete)
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(text = "미 완료", fontSize = 13.sp, color = Color.Gray)
            }
        }

        BucketStatus.IN_PROGRESS -> {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(InProgressChipBackground)
                    .clickable(onClick = onComplete)
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(text = "도전 중", fontSize = 13.sp, color = InProgressChipTextColor)
            }
        }

        BucketStatus.COMPLETED -> {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(CompletedChipBackground)
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(text = "완료", fontSize = 13.sp, color = CompletedChipTextColor)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BucketListScreenPreview() {
    BucketListScreen()
}
