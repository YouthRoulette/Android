package com.kuit.youthroulette.ui.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PendingResultTab(
    results:List<ResultItemUiModel>,
    onProofClick:(Int)->Unit,
    modifier:Modifier= Modifier
){
    if(results.isEmpty()){
        Box(
            modifier=modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(text="아직 도전 중인 버킷이 없어요. 룰렛을 돌려 오늘의 도전을 시작해보세요!")
        }
    }
    else{
        LazyColumn(
            modifier=modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items=results,
                key={result->result.bucketId}
            ){result->
                ResultCard(
                    result=result,
                    onProofClick={onProofClick(result.bucketId)}
                )
            }
        }
    }
}