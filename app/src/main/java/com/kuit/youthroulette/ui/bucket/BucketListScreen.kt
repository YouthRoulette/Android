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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuit.youthroulette.data.MockData
import com.kuit.youthroulette.model.BucketItem
import com.kuit.youthroulette.model.BucketStatus
import com.kuit.youthroulette.ui.component.CommonTopBar

private val BannerBackground = Color(0xFFFDEEE3)
private val BannerTextColor = Color(0xFF8A5A34)
private val BannerHighlightColor = Color(0xFFEE8A3D)

private val TabRowBackground = Color(0xFFF4F2EF)
private val SelectedTabBackground = Color(0xFFFFFFFF)
private val SelectedTabTextColor = Color(0xFF2B2B2B)
private val UnselectedTabTextColor = Color(0xFFA6A29C)

private val CardBackground = Color(0xFFFFFFFF)
private val CardBorderColor = Color(0xFFF0EDE8)
private val InProgressBackground = Color(0xFFF3E7DC)
private val InProgressBorderColor = Color(0xFFDCC0A4)
private val CompletedBackground = Color(0xFFDFF5E3)
private val CompletedBorderColor = Color(0xFFBBE6C6)

private val NotStartedChipBackground = Color(0xFFF1EFEC)
private val NotStartedChipTextColor = Color(0xFF6E6A64)
private val InProgressChipBackground = Color(0xFFE4CBAE)
private val InProgressChipTextColor = Color(0xFF7A4B24)
private val CompletedChipBackground = Color(0xFFBFEACB)
private val CompletedChipTextColor = Color(0xFF25793D)

private val IconBackgroundPalette = listOf(
    Color(0xFFD6D2F0),
    Color(0xFFF6D9C4),
    Color(0xFFF2C79A),
    Color(0xFFF5E1A0),
    Color(0xFFB7DFD1),
    Color(0xFFBFD9F2),
    Color(0xFFEBC6C6),
    Color(0xFFD7E8B8)
)

private const val ROULETTE_SLOT_CAPACITY = 8

private enum class BucketFilter(val label: String) {
    ALL("전체"),
    IN_PROGRESS("도전 중"),
    COMPLETED("완료")
}

@Composable
fun BucketListScreen() {
    var bucketItems by remember { mutableStateOf(MockData.bucketItems) }
    var selectedFilter by remember { mutableStateOf(BucketFilter.ALL) }

    val notStartedCount = bucketItems.count { it.status == BucketStatus.NOT_STARTED }
    val inProgressCount = bucketItems.count { it.status == BucketStatus.IN_PROGRESS }
    val completedCount = bucketItems.count { it.status == BucketStatus.COMPLETED }

    val filteredItems = when (selectedFilter) {
        BucketFilter.ALL -> bucketItems
        BucketFilter.IN_PROGRESS -> bucketItems.filter { it.status == BucketStatus.IN_PROGRESS }
        BucketFilter.COMPLETED -> bucketItems.filter { it.status == BucketStatus.COMPLETED }
    }

    Scaffold(
        topBar = { CommonTopBar(title = "버킷리스트") }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {
            NotStartedBanner(notStartedCount = notStartedCount)

            Spacer(modifier = Modifier.height(16.dp))

            BucketFilterTabs(
                selectedFilter = selectedFilter,
                allCount = bucketItems.size,
                inProgressCount = inProgressCount,
                completedCount = completedCount,
                onSelect = { selectedFilter = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredItems, key = { it.id }) { item ->
                    BucketListItem(
                        item = item,
                        iconBackgroundColor = IconBackgroundPalette[item.id % IconBackgroundPalette.size],
                        onComplete = {
                            bucketItems = bucketItems.map {
                                if (it.id == item.id) it.copy(status = BucketStatus.COMPLETED) else it
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun NotStartedBanner(notStartedCount: Int) {
    val cappedCount = notStartedCount.coerceAtMost(ROULETTE_SLOT_CAPACITY)
    val statusMessage = if (cappedCount >= ROULETTE_SLOT_CAPACITY) {
        "가득 찼어요 🎉"
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
                text = "미도전 버킷 ",
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
    allCount: Int,
    inProgressCount: Int,
    completedCount: Int,
    onSelect: (BucketFilter) -> Unit
) {
    val counts = mapOf(
        BucketFilter.ALL to allCount,
        BucketFilter.IN_PROGRESS to inProgressCount,
        BucketFilter.COMPLETED to completedCount
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TabRowBackground, RoundedCornerShape(16.dp))
            .padding(4.dp)
    ) {
        BucketFilter.entries.forEach { filter ->
            val selected = filter == selectedFilter
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (selected) SelectedTabBackground else Color.Transparent)
                    .clickable { onSelect(filter) }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${filter.label} ${counts[filter] ?: 0}",
                    fontSize = 14.sp,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                    color = if (selected) SelectedTabTextColor else UnselectedTabTextColor
                )
            }
        }
    }
}

@Composable
private fun BucketListItem(
    item: BucketItem,
    iconBackgroundColor: Color,
    onComplete: () -> Unit
) {
    val background = when (item.status) {
        BucketStatus.NOT_STARTED -> CardBackground
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
            Text(text = item.emoji, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = item.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
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
                    .background(NotStartedChipBackground)
                    .clickable(onClick = onComplete)
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(text = "미도전", fontSize = 13.sp, color = NotStartedChipTextColor)
            }
        }

        BucketStatus.IN_PROGRESS -> {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(InProgressChipBackground)
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
