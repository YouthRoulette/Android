package com.kuit.youthroulette.ui.result

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.youthroulette.data.ResultRepository
import com.kuit.youthroulette.model.FeedPost
import com.kuit.youthroulette.model.PendingBucket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ResultViewModel : ViewModel() {

    private val resultRepository = ResultRepository()

    private val _uiState = MutableStateFlow(ResultUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadResults()
    }

    private fun loadResults() {
        viewModelScope.launch {
            try {
                val pendingBuckets = resultRepository.getPendingBuckets()
                val completedPosts = resultRepository.getCompletedPosts()

                Log.d("ResultViewModel", "pendingBuckets: $pendingBuckets")
                Log.d("ResultViewModel", "completedPosts: $completedPosts")

                _uiState.update {
                    it.copy(
                        pendingResults = pendingBuckets.map { bucket ->
                            bucket.toResultItemUiModel()
                        },
                        completedResults = completedPosts.map { post ->
                            post.toResultItemUiModel()
                        }
                    )
                }
            } catch (e: Exception) {
                Log.e("ResultViewModel", "loadResults failed", e)

                _uiState.update {
                    it.copy(
                        pendingResults = emptyList(),
                        completedResults = emptyList()
                    )
                }
            }
        }
    }
}

private fun PendingBucket.toResultItemUiModel(): ResultItemUiModel {
    return ResultItemUiModel(
        bucketId = bucketId,
        title = title,
        isCompleted = false,
        content = content,
        proofImageUrl = null
    )
}

private fun FeedPost.toResultItemUiModel(): ResultItemUiModel {
    return ResultItemUiModel(
        bucketId = bucketId,
        title = bucketTitle,
        isCompleted = true,
        content = content,
        proofImageUrl = imageUrl
    )
}