package com.kuit.youthroulette.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kuit.youthroulette.ui.bucket.AddBucketScreen
import com.kuit.youthroulette.ui.bucket.BucketListScreen
import com.kuit.youthroulette.ui.friend.FriendScreen
import com.kuit.youthroulette.ui.mypage.MyPageScreen
import com.kuit.youthroulette.ui.proof.ProofScreen
import com.kuit.youthroulette.ui.result.ResultScreen
import com.kuit.youthroulette.ui.roulette.RouletteScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.ROULETTE,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.ROULETTE) {
                RouletteScreen()
            }

            composable(Routes.BUCKET) {
                BucketListScreen()
            }

            composable(Routes.ADD_BUCKET) {
                AddBucketScreen()
            }

            composable(Routes.RESULT) {
                ResultScreen()
            }

            composable(Routes.PROOF) {
                ProofScreen()
            }

            composable(Routes.FRIEND) {
                FriendScreen()
            }

            composable(Routes.MYPAGE) {
                MyPageScreen()
            }
        }
    }
}