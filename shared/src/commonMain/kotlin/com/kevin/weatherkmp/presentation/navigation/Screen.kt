package com.kevin.weatherkmp.presentation.navigation

sealed class Screen(
    val route: String,
    val title: String
) {

    data object Home :
        Screen(
            route = "home",
            title = "Home"
        )

    data object SearchHistory :
        Screen(
            route = "search_history",
            title = "History"
        )

    data object Settings :
        Screen(
            route = "settings",
            title = "Settings"
        )
}