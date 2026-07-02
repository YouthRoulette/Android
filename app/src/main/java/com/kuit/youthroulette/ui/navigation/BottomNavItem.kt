package com.kuit.youthroulette.ui.navigation

data class BottomNavItem(       //이미지는 예시입니다
    val route: String,
    val label: String,
    val iconText: String
)

val bottomNavItems = listOf(
    BottomNavItem(
        route = Routes.ROULETTE,
        label = "룰렛",
        iconText = "🎯"
    ),
    BottomNavItem(
        route = Routes.BUCKET,
        label = "버킷",
        iconText = "📋"
    ),
    BottomNavItem(
        route = Routes.RESULT,
        label = "결과",
        iconText = "✅"
    ),
    BottomNavItem(
        route = Routes.FRIEND,
        label = "친구",
        iconText = "👥"
    ),
    BottomNavItem(
        route = Routes.MYPAGE,
        label = "마이",
        iconText = "👤"
    )
)