package com.kuit.youthroulette.ui.proof

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FriendTagSelector(
    friendTagText: String,
    onFriendTagTextChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = friendTagText,
        onValueChange = onFriendTagTextChange,
        placeholder = {
            Text(
                text = "🔍  친구 이름을 입력해보세요",
                color = Color(0xFFAAA2A0)
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        shape = RoundedCornerShape(16.dp),
        singleLine = true
    )
}