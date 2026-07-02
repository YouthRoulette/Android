package com.kuit.youthroulette.ui.result

import android.R.id.tabs
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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

    Scaffold(
        containerColor = Color(0xFFFFFBF7),
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
            Spacer(modifier = Modifier.height(20.dp))

            ResultSegmentedTab(
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { index ->
                    selectedTabIndex = index
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

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