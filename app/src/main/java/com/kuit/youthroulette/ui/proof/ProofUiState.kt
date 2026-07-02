package com.kuit.youthroulette.ui.proof

data class ProofUiState(
    val imageUrl:String?=null,
    val content:String="",
    val isPublic:Boolean=true,
    val friendTagText:String=""
)