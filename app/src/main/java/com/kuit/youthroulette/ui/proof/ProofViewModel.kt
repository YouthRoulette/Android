package com.kuit.youthroulette.ui.proof

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ProofViewModel : ViewModel(){
    private val _uiState= MutableStateFlow(ProofUiState())
    val uiState: StateFlow<ProofUiState> =_uiState

    fun updateContent(content:String){
        _uiState.update{
            it.copy(content=content)
        }
    }

    fun updatePublic(isPublic:Boolean){
        _uiState.update{
            it.copy(isPublic=isPublic)
        }
    }

    fun updateFriendTagText(text:String){
        _uiState.update{
            it.copy(friendTagText = text)
        }
    }
    fun onImageUploadClick(){
        //TODO 나중에 갤러리 연결
    }

    fun submitProof(bucketId: Int){
        //TODO 나중에 서버에 인증 데이터 저장
    }
}