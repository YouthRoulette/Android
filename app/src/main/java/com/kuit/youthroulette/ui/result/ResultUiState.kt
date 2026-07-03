package com.kuit.youthroulette.ui.result

data class ResultUiState(
    val pendingResults: List<ResultItemUiModel> = emptyList(),
    val completedResults: List<ResultItemUiModel> = emptyList()
)

data class ResultItemUiModel(
    val bucketId: Int,
    val title: String,
    val isCompleted: Boolean = false,
    val content: String = "",
    val proofImageUrl: String? = null,
    val emojiIndex: Int = 0
)