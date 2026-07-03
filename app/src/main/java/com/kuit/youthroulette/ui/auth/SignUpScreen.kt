package com.kuit.youthroulette.ui.auth

import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuit.youthroulette.ui.component.CommonTopBar

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    var loginId by rememberSaveable { mutableStateOf("") }
    var nickname by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val idRegex = Regex("^[A-Za-z0-9_]+$")

    val isIdValid = loginId.length in 3..50 && idRegex.matches(loginId)
    val isNicknameValid = nickname.length in 2..20
    val isPasswordValid = password.length >= 8
    val isInputValid = isIdValid && isNicknameValid && isPasswordValid

    Scaffold(
        topBar = {
            CommonTopBar(title = "회원가입")
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "회원가입",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "아이디, 닉네임, 비밀번호를 입력해주세요",
                modifier = Modifier.padding(top = 8.dp)
            )

            OutlinedTextField(
                value = loginId,
                onValueChange = { input ->
                    loginId = input.filter { char ->
                        char in 'A'..'Z' || char in 'a'..'z' || char in '0'..'9' || char == '_'
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                label = { Text(text = "아이디") },
                supportingText = {
                    if (loginId.isNotBlank() && !isIdValid) {
                        Text(text = "아이디는 영문/숫자/언더스코어 3~50자예요")
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii)
            )

            OutlinedTextField(
                value = nickname,
                onValueChange = { nickname = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                label = { Text(text = "닉네임") },
                supportingText = {
                    if (nickname.isNotBlank() && !isNicknameValid) {
                        Text(text = "닉네임은 2~20자예요")
                    }
                },
                singleLine = true
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                label = { Text(text = "비밀번호") },
                supportingText = {
                    if (password.isNotBlank() && !isPasswordValid) {
                        Text(text = "비밀번호는 8자 이상이에요")
                    }
                },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Button(
                onClick = {
                    viewModel.signUp(loginId, nickname, password) {
                        Toast.makeText(context, "회원가입이 완료됐어요! 로그인해주세요.", Toast.LENGTH_SHORT).show()
                        onSignUpSuccess()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                enabled = isInputValid && !uiState.isLoading,
                contentPadding = PaddingValues(16.dp)
            ) {
                Text(text = if (uiState.isLoading) "가입 중..." else "회원가입")
            }
        }
    }
}
