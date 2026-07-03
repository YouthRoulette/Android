package com.kuit.youthroulette.ui.proof

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuit.youthroulette.ui.component.CommonTopBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun ProofScreen(
    bucketId: Int,
    onBackClick: () -> Unit,
    onProofComplete: () -> Unit,
    viewModel: ProofViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        viewModel.updateSelectedImage(uri)
    }

    LaunchedEffect(uiState.submitSuccess) {
        if (uiState.submitSuccess) {
            onBackClick()
        }
    }

    Scaffold(
        topBar = {
            CommonTopBar(
                title = "완료 인증하기",
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "뒤로가기"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "인증 사진",
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            ImageUploadBox(
                imageUri = uiState.selectedImageUri,
                onClick = {
                    imagePickerLauncher.launch("image/*")
                }
            )



            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "인증 내용",
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = uiState.content,
                onValueChange = viewModel::updateContent,
                placeholder = {
                    Text(
                        text = "오늘의 인증 내용을 작성해주세요",
                        color = Color(0xFFAAA2A0)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                singleLine = false
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "공개 범위",
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

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
                text = "친구 태그",
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            FriendTagSelector(
                friendTagText = uiState.friendTagText,
                friends = uiState.friends,
                selectedFriendIds = uiState.selectedFriendIds,
                onFriendTagTextChange = viewModel::updateFriendTagText,
                onFriendClick = viewModel::toggleFriendSelection
            )

            if (uiState.errorMessage != null) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = uiState.errorMessage ?: "",
                    color = Color.Red
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    viewModel.submitProof(
                        bucketId = bucketId,
                        context = context
                    )
                },
                enabled = !uiState.isSubmitting,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF8B6F5A),
                    disabledContainerColor = Color(0xFFD0C2B8)
                )
            ) {
                Text(
                    text = if (uiState.isSubmitting) {
                        "전송 중..."
                    } else {
                        "인증 완료하기"
                    },
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}