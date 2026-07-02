package com.kuit.youthroulette.ui.proof

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuit.youthroulette.ui.component.CommonTopBar

@Composable
fun ProofScreen(
    bucketId:Int,
    onBackClick:()->Unit,
    onProofComplete:()->Unit,
    viewModel: ProofViewModel=viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CommonTopBar(
                title = "완료 인증하기",
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Text(
                            text = "‹",
                            fontSize = 32.sp,
                            color = Color(0xFF9A8D84)
                        )
                    }
                }
            )
        }
    ) {innerPadding->
        Column(
            modifier=Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ){
            Spacer(modifier= Modifier.height(32.dp))
            ImageUploadBox(
                onClick=viewModel::onImageUploadClick
            )

            Spacer(modifier= Modifier.height(24.dp))

            Text(
                text = "한 줄 소감을 남겨주세요!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2B2724)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.content,
                onValueChange = viewModel::updateContent,
                placeholder = {
                    Text(
                        text = "오늘의 도전 기록을 남겨보세요 :)",
                        color = Color(0xFFAAA2A0)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(88.dp),
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "공개 여부 선택",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2B2724)
            )

            Spacer(modifier = Modifier.height(12.dp))

            VisibilitySelector(
                isPublic = uiState.isPublic,
                onPublicSelected = {
                    viewModel.updatePublic(true)
                },
                onPrivateSelected = {
                    viewModel.updatePublic(false)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "친구 태그 (선택)",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2B2724)
            )

            Spacer(modifier = Modifier.height(12.dp))

            FriendTagSelector(
                friendTagText = uiState.friendTagText,
                onFriendTagTextChange = viewModel::updateFriendTagText
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    viewModel.submitProof(bucketId)
                    onProofComplete()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF2B487)
                )
            ) {
                Text(
                    text = "완료 인증하기",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

