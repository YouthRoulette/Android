package com.kuit.youthroulette.ui.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CompletedResultTab(
    results:List<ResultItemUiModel>,
    modifier:Modifier= Modifier
){
    if(results.isEmpty()){
        Text(text="아직 인증한 버킷이 없어요")
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
                    result=result
                )
            }
        }
    }
}