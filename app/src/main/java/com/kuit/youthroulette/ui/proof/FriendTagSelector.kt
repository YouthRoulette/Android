package com.kuit.youthroulette.ui.proof

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kuit.youthroulette.model.Friend

@Composable
fun FriendTagSelector(
    friendTagText: String,
    friends: List<Friend>,
    selectedFriendIds: List<Int>,
    onFriendTagTextChange: (String) -> Unit,
    onFriendClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredFriends =
        if (friendTagText.isBlank()) {
            friends
        } else {
            friends.filter { friend ->
                friend.name.contains(friendTagText, ignoreCase = true)
            }
        }

    Column(
        modifier = modifier.fillMaxWidth()
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
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            shape = RoundedCornerShape(16.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (filteredFriends.isEmpty() && friendTagText.isNotBlank()) {
            Text(
                text = "검색 결과가 없습니다",
                color = Color(0xFFAAA2A0),
                modifier = Modifier.padding(vertical = 8.dp)
            )
        } else {
            filteredFriends.forEach { friend ->
                FriendTagItem(
                    friend = friend,
                    isSelected = friend.id in selectedFriendIds,
                    onClick = {
                        onFriendClick(friend.id)
                    }
                )
            }
        }
    }
}

@Composable
private fun FriendTagItem(
    friend: Friend,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isSelected,
            onCheckedChange = {
                onClick()
            }
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = friend.name,
            color = Color.Black
        )
    }
}