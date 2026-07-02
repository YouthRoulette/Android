package com.kuit.youthroulette.ui.friend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun FriendNewsTab(feeds: List<FeedUiModel>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(feeds, key = { it.id }) { feed ->
            FriendFeedCard(feed = feed)
        }
    }
}
