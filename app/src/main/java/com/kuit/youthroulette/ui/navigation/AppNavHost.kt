package com.kuit.youthroulette.ui.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kuit.youthroulette.ui.bucket.AddBucketScreen
import com.kuit.youthroulette.ui.bucket.BucketListScreen
import com.kuit.youthroulette.ui.friend.FriendScreen
import com.kuit.youthroulette.ui.mypage.MyPageScreen
import com.kuit.youthroulette.ui.onboarding.OnboardingScreen
import com.kuit.youthroulette.ui.proof.ProofScreen
import com.kuit.youthroulette.ui.result.ResultScreen
import com.kuit.youthroulette.ui.roulette.RouletteScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        Routes.ROULETTE,
        Routes.BUCKET,
        Routes.RESULT,
        Routes.FRIEND,
        Routes.MYPAGE
    )

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(
                    navController = navController
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.ONBOARDING,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.ONBOARDING) {
                OnboardingScreen(
                    onStartClick = {
                        navController.navigate(Routes.ROULETTE) {
                            popUpTo(Routes.ONBOARDING) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

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
                ResultScreen(
                    onProofClick = { bucketId ->
                        navController.navigate(Routes.proof(bucketId))
                    }
                )
            }

            composable(Routes.PROOF) { backStackEntry ->
                val bucketId = backStackEntry.arguments
                    ?.getString("bucketId")
                    ?.toIntOrNull()
                    ?: 0

                ProofScreen(
                    bucketId = bucketId,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onProofComplete = {
                        navController.popBackStack()
                    }
                )
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