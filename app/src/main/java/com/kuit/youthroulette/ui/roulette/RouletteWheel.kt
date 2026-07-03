package com.kuit.youthroulette.ui.roulette

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuit.youthroulette.data.MockData
import com.kuit.youthroulette.model.BucketItem
import com.kuit.youthroulette.model.BucketStatus
import com.kuit.youthroulette.ui.bucket.IconBackgroundPalette
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

private val WheelBorderColor = Color(0xFFFFC98B)
private val PointerColor = Color(0xFFFF9F5A)
private val StartButtonColor = Color(0xFFFF9F5A)
private val StartButtonDisabledColor = Color(0xFFE0DCD4)
private val LabelColor = Color(0xFF3A3A3A)

private const val SpinDurationMs = 5000

@Composable
fun RouletteWheel(
    items: List<BucketItem>,
    modifier: Modifier = Modifier,
    hasActiveChallenge: Boolean = false,
    onSpinningChange: (Boolean) -> Unit = {},
    onResult: (BucketItem) -> Unit = {}
) {
    if (items.isEmpty()) return

    val sliceAngle = 360f / items.size
    val rotation = remember { Animatable(0f) }
    var isSpinning by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    // 버킷이 하나뿐이면 결과가 항상 정해져 있으므로 스핀을 막고,
    // 이미 도전 중인 버킷이 있으면(동시에 하나만 도전 가능) 스핀을 막는다
    val canSpin = items.size > 1 && !hasActiveChallenge

    fun spin() {
        if (isSpinning || !canSpin) return
        isSpinning = true
        onSpinningChange(true)
        val targetIndex = Random.nextInt(items.size)
        scope.launch {
            // 포인터는 12시 방향에 고정되어 있으므로, targetIndex 조각의 중앙이
            // 그 방향에 오도록 남은 회전각(delta)을 구하고 여러 바퀴를 더해 돌린다.
            val currentMod = ((rotation.value % 360f) + 360f) % 360f
            val desiredResidual =
                ((360f - (sliceAngle * targetIndex + sliceAngle / 2f)) % 360f + 360f) % 360f
            var delta = desiredResidual - currentMod
            if (delta <= 0f) delta += 360f
            val extraSpins = 360f * (5 + Random.nextInt(3))

            rotation.animateTo(
                targetValue = rotation.value + delta + extraSpins,
                animationSpec = tween(
                    durationMillis = SpinDurationMs,
                    easing = CubicBezierEasing(0.12f, 0.68f, 0.2f, 1f)
                )
            )
            isSpinning = false
            onSpinningChange(false)
            onResult(items[targetIndex])
        }
    }

    Box(
        modifier = modifier.aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        // 휠 원
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(2.dp, WheelBorderColor, CircleShape)
                .padding(6.dp)
                .clip(CircleShape)
                .background(Color.White)
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .rotate(rotation.value)
            ) {
                val diameter = maxWidth

                Canvas(modifier = Modifier.fillMaxSize()) {
                    val radius = size.minDimension / 2f
                    val center = Offset(size.width / 2f, size.height / 2f)
                    val topLeft = Offset(center.x - radius, center.y - radius)
                    val arcSize = Size(radius * 2f, radius * 2f)

                    items.forEachIndexed { index, item ->
                        drawArc(
                            color = IconBackgroundPalette[item.colorIndex % IconBackgroundPalette.size],
                            startAngle = -90f + index * sliceAngle,
                            sweepAngle = sliceAngle,
                            useCenter = true,
                            topLeft = topLeft,
                            size = arcSize
                        )
                    }

                    items.indices.forEach { index ->
                        val angleRad = Math.toRadians((-90.0 + index * sliceAngle))
                        val end = Offset(
                            x = center.x + radius * cos(angleRad).toFloat(),
                            y = center.y + radius * sin(angleRad).toFloat()
                        )
                        drawLine(
                            color = Color.White,
                            start = center,
                            end = end,
                            strokeWidth = 3.dp.toPx()
                        )
                    }
                }

                items.forEachIndexed { index, item ->
                    val midAngleRad =
                        Math.toRadians((-90.0 + sliceAngle * index + sliceAngle / 2.0))
                    val labelRadius = diameter / 2f * 0.6f
                    val offsetX = labelRadius * cos(midAngleRad).toFloat()
                    val offsetY = labelRadius * sin(midAngleRad).toFloat()

                    Text(
                        text = item.title,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = LabelColor,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .offset(x = offsetX, y = offsetY)
                            .width(diameter * 0.34f)
                    )
                }
            }
        }

        // 포인터(삼각형)
        Canvas(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-15).dp)
                .size(width = 26.dp, height = 20.dp)
        ) {
            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(size.width, 0f)
                lineTo(size.width / 2f, size.height)
                close()
            }
            drawPath(path, color = PointerColor)
        }

        // 중앙 start 버튼
        CenterStartButton(
            canSpin = canSpin,
            isSpinning = isSpinning,
            hasActiveChallenge = hasActiveChallenge,
            onClick = { spin() }
        )
    }
}

@Composable
private fun BoxScope.CenterStartButton(
    canSpin: Boolean,
    isSpinning: Boolean,
    hasActiveChallenge: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .align(Alignment.Center)
            .fillMaxSize(0.3f)
            .clip(CircleShape)
            .background(if (canSpin) StartButtonColor else StartButtonDisabledColor)
            .clickable(enabled = canSpin && !isSpinning, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "START",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = when {
                    hasActiveChallenge -> "도전 중인 버킷이 있어요"
                    !canSpin -> "버킷이 더 필요해요"
                    isSpinning -> "돌아가는 중"
                    else -> "눌러서 돌리기"
                },
                color = Color.White,
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview(showBackground = true, name = "여러 개")
private fun RouletteWheelPreview() {
    RouletteWheel(
        items = MockData.bucketItems.filter { it.status == BucketStatus.NOT_STARTED },
        modifier = Modifier
            .size(320.dp)
            .padding(16.dp)
    )
}

@Composable
@Preview(showBackground = true, name = "한 개 (스핀 비활성화)")
private fun RouletteWheelSingleItemPreview() {
    RouletteWheel(
        items = listOf(BucketItem(id = 1, title = "혼자 여행 가기")),
        modifier = Modifier
            .size(320.dp)
            .padding(16.dp)
    )
}

@Composable
@Preview(showBackground = true, name = "도전 중 버킷 있음 (스핀 비활성화)")
private fun RouletteWheelActiveChallengePreview() {
    RouletteWheel(
        items = MockData.bucketItems.filter { it.status == BucketStatus.NOT_STARTED },
        hasActiveChallenge = true,
        modifier = Modifier
            .size(320.dp)
            .padding(16.dp)
    )
}