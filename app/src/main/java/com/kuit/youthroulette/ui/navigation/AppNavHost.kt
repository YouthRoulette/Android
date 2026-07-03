package com.kuit.youthroulette.ui.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kuit.youthroulette.data.SessionManager
import com.kuit.youthroulette.ui.auth.LoginScreen
import com.kuit.youthroulette.ui.auth.SignUpScreen
import com.kuit.youthroulette.ui.bucket.BucketListScreen
import com.kuit.youthroulette.ui.friend.FriendScreen
import com.kuit.youthroulette.ui.mypage.MyPageScreen
import com.kuit.youthroulette.ui.proof.ProofScreen
import com.kuit.youthroulette.ui.result.ResultScreen
import com.kuit.youthroulette.ui.roulette.RouletteScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val mainRoutes = listOf(
        Routes.ROULETTE,
        Routes.BUCKET,
        Routes.RESULT,
        Routes.FRIEND,
        Routes.MYPAGE
    )

    val showBottomBar = currentRoute in mainRoutes

    LaunchedEffect(currentRoute) {
        val route = currentRoute
        if (route != null && route in mainRoutes) {
            SessionManager.saveLastRoute(route)
        }
    }

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
        val startDestination = remember {
            val destination = when {
                !SessionManager.hasLaunchedBefore -> Routes.LOGIN
                SessionManager.isLoggedIn() && SessionManager.lastRoute in mainRoutes -> SessionManager.lastRoute!!
                SessionManager.isLoggedIn() -> Routes.ROULETTE
                else -> Routes.LOGIN
            }
            SessionManager.markLaunched()
            destination
        }

        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.LOGIN) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Routes.ROULETTE) {
                            popUpTo(Routes.LOGIN) {
                                inclusive = true
                            }
                        }
                    },
                    onNavigateToSignUp = {
                        navController.navigate(Routes.SIGNUP)
                    }
                )
            }

            composable(Routes.SIGNUP) {
                SignUpScreen(
                    onSignUpSuccess = {
                        navController.popBackStack()
                    },
                    onNavigateToLogin = {
                        navController.popBackStack()
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
                MyPageScreen(
                    onLogout = {
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}