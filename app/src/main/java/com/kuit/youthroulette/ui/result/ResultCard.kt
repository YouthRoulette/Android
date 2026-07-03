package com.kuit.youthroulette.ui.result

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ResultCard(
    result: ResultItemUiModel,
    onProofClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = result.title)

            if (result.isCompleted) {
                Text(
                    text = "인증 완료",
                    modifier = Modifier.padding(top = 8.dp)
                )

                if (result.content.isNotBlank()) {
                    Text(
                        text = result.content,
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
            } else {
                Button(
                    onClick = onProofClick,
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    Text(text = "인증하기")
                }
            }
        }
    }
}