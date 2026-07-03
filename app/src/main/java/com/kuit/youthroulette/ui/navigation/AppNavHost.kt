package com.kuit.youthroulette.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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
                RouletteScreen(
                    onNavigateToBucket = {
                        navController.navigate(Routes.BUCKET) {
                            popUpTo(Routes.ROULETTE) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }

            composable(Routes.BUCKET) {
                BucketListScreen()
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