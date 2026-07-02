package com.kuit.youthroulette.ui.result

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuit.youthroulette.ui.component.CommonTopBar

@Composable
fun ResultScreen(
    onProofClick:(Int)->Unit,
    viewModel: ResultViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTabIndex by rememberSaveable{ mutableStateOf(0) }//현재 선택된 탭 번호
    val tabs=listOf("미완료","인증완료")
    Scaffold(
        topBar = {
            CommonTopBar(
                title="결과"
            )
        }
    ) {innerPadding->
        Column(
            modifier= Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            TabRow(
                selectedTabIndex=selectedTabIndex
            ){
                tabs.forEachIndexed{index,title->
                    Tab(
                        selected = selectedTabIndex==index,
                        onClick = {
                            selectedTabIndex=index
                        },
                        text={
                            Text(text=title)
                        }
                    )
                }
            }

            when (selectedTabIndex){
                0->PendingResultTab(
                    results=uiState.pendingResults,
                    onProofClick=onProofClick,
                    modifier=Modifier.fillMaxSize()
                )

                1->CompletedResultTab(
                    results=uiState.completedResults,
                    modifier=Modifier.fillMaxSize()
                )
            }
        }
    }
}