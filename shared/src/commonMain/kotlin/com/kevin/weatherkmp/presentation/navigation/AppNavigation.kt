package com.kevin.weatherkmp.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*

import com.kevin.weatherkmp.presentation.components.BottomBar
import com.kevin.weatherkmp.presentation.searchhistory.SearchHistoryScreen
import com.kevin.weatherkmp.presentation.settings.SettingsScreen
import com.kevin.weatherkmp.presentation.home.HomeScreen

@Composable
fun AppNavigation() {

    val navController =
        rememberNavController()

    Scaffold(

        bottomBar = {

            BottomBar(navController)
        }

    ) { paddingValues ->

        NavHost(

            navController = navController,

            startDestination = Screen.Home.route,

            modifier = Modifier
                .padding(paddingValues)

        ) {

            composable(
                Screen.Home.route
            ) {

                HomeScreen()
            }

            composable(
                Screen.SearchHistory.route
            ) {

                SearchHistoryScreen()
            }

            composable(
                Screen.Settings.route
            ) {

                SettingsScreen()
            }
        }
    }
}