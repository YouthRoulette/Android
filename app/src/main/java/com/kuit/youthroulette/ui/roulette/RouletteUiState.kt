package com.kuit.youthroulette.ui.roulette

import com.kuit.youthroulette.model.BucketItem

data class RouletteUiState(
    val buckets: List<BucketItem> = emptyList(),
    val selectedBucket: BucketItem? = null,
    val isSpinning: Boolean = false
)