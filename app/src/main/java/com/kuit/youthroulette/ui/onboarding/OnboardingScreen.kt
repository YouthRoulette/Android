package com.kuit.youthroulette.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kuit.youthroulette.data.UserRepository
import com.kuit.youthroulette.ui.component.CommonTopBar

@Composable
fun OnboardingScreen(
    onStartClick: () -> Unit
) {
    var nickname by rememberSaveable { mutableStateOf("") }
    var idText by rememberSaveable { mutableStateOf("") }

    val id = idText.toIntOrNull()
    val isInputValid = nickname.isNotBlank() && id != null

    Scaffold(
        topBar = {
            CommonTopBar(
                title = "청춘룰렛"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "닉네임과 아이디를 입력해주세요")

            OutlinedTextField(
                value = nickname,
                onValueChange = { nickname = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                label = {
                    Text(text = "닉네임")
                }
            )

            OutlinedTextField(
                value = idText,
                onValueChange = { idText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                label = {
                    Text(text = "아이디")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                supportingText = {
                    if (idText.isNotBlank() && id == null) {
                        Text(text = "아이디는 숫자로 입력해주세요")
                    }
                }
            )

            Button(
                onClick = {
                    UserRepository.saveUser(
                        id = id!!,
                        nickname = nickname
                    )
                    onStartClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                enabled = isInputValid,
                contentPadding = PaddingValues(16.dp)
            ) {
                Text(text = "시작하기")
            }
        }
    }
}