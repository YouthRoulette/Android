package com.kuit.youthroulette.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuit.youthroulette.data.UserRepository
import com.kuit.youthroulette.data.remote.ApiException
import com.kuit.youthroulette.ui.component.CommonTopBar
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(
    onBackClick: () -> Unit,
    onSignupComplete: () -> Unit
) {
    var id by rememberSaveable { mutableStateOf("") }
    var nickname by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isSigningUp by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val isInputValid = id.isNotBlank() && nickname.isNotBlank() && password.isNotBlank() && !isSigningUp

    Scaffold(
        topBar = {
            CommonTopBar(
                title = "회원가입",
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Text(
                            text = "‹",
                            fontSize = 32.sp,
                            color = Color(0xFF9A8D84)
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "아이디, 닉네임, 비밀번호를 입력해주세요")

            OutlinedTextField(
                value = id,
                onValueChange = { input -> id = input },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                label = {
                    Text(text = "아이디")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Ascii
                ),
                singleLine = true
            )

            OutlinedTextField(
                value = nickname,
                onValueChange = { input -> nickname = input },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                label = {
                    Text(text = "닉네임")
                },
                singleLine = true
            )

            OutlinedTextField(
                value = password,
                onValueChange = { input -> password = input },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                label = {
                    Text(text = "비밀번호")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )

            Button(
                onClick = {
                    isSigningUp = true
                    coroutineScope.launch {
                        UserRepository.signup(loginId = id, nickname = nickname, password = password)
                            .onSuccess {
                                onSignupComplete()
                            }
                            .onFailure { error ->
                                isSigningUp = false
                                val message = if (error is ApiException) {
                                    error.errorResponse.errors?.firstOrNull()?.reason
                                        ?: error.errorResponse.message
                                } else {
                                    "네트워크 연결을 확인해주세요."
                                }
                                snackbarHostState.showSnackbar(message)
                            }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                enabled = isInputValid,
                contentPadding = PaddingValues(16.dp)
            ) {
                Text(text = "가입하기")
            }
        }
    }
}
