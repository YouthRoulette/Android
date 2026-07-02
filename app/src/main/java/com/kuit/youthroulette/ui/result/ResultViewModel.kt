package com.kuit.youthroulette.ui.result

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ResultViewModel : ViewModel(){
    private val _uiState=MutableStateFlow(
        ResultUiState(
            pendingResults = listOf(
                ResultItemUiModel(
                    bucketId = 1,
                    title = "친구랑 사진 찍기",
                    isCompleted = false
                ),
                ResultItemUiModel(
                    bucketId = 2,
                    title = "새로운 음식 먹기",
                    isCompleted = false
                )
            ),
            completedResults = listOf(
                ResultItemUiModel(
                    bucketId = 3,
                    title = "산책하기",
                    isCompleted = true,
                    content="산책완료"
                )
            )
        )
    )
    val uiState: StateFlow<ResultUiState> = _uiState.asStateFlow()
}