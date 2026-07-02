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
    var id by rememberSaveable { mutableStateOf("") }
    var nickname by rememberSaveable { mutableStateOf("") }

    val idRegex = Regex("^[A-Za-z0-9]+$")
    val nicknameRegex = Regex("^[A-Za-z]+$")

    val isIdValid = id.isNotBlank() && idRegex.matches(id)
    val isNicknameValid = nickname.isNotBlank() && nicknameRegex.matches(nickname)
    val isInputValid = isIdValid && isNicknameValid

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
                value = id,
                onValueChange = { input ->
                    id = input.filter { char ->
                        char in 'A'..'Z' || char in 'a'..'z' || char in '0'..'9'
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                label = {
                    Text(text = "아이디")
                },
                supportingText = {
                    if (id.isNotBlank() && !isIdValid) {
                        Text(text = "아이디는 영어와 숫자만 입력할 수 있어요")
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Ascii
                ),
                singleLine = true
            )

            OutlinedTextField(
                value = nickname,
                onValueChange = { input ->
                    nickname = input.filter { char ->
                        char in 'A'..'Z' || char in 'a'..'z'
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                label = {
                    Text(text = "닉네임")
                },
                supportingText = {
                    if (nickname.isNotBlank() && !isNicknameValid) {
                        Text(text = "닉네임은 영어만 입력할 수 있어요")
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Ascii
                ),
                singleLine = true
            )

            Button(
                onClick = {
                    UserRepository.saveUser(
                        id = id,
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