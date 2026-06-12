package com.kevin.weatherkmp.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kevin.weatherkmp.presentation.navigation.Screen

@Composable
fun BottomBar(
    navController: NavHostController
) {

    val backStackEntry =
        navController.currentBackStackEntryAsState()

    val currentRoute =
        backStackEntry.value?.destination?.route

    NavigationBar {

        listOf(

            Screen.Home,
            Screen.SearchHistory,
            Screen.Settings

        ).forEach { screen ->

            val icon = when (screen) {

                Screen.Home ->
                    Icons.Default.Home

                Screen.SearchHistory ->
                    Icons.Default.History

                Screen.Settings ->
                    Icons.Default.Settings
            }

            NavigationBarItem(

                selected = currentRoute == screen.route,

                onClick = {

                    navController.navigate(screen.route)
                },

                icon = {

                    Icon(
                        imageVector = icon,
                        contentDescription = null
                    )
                },

                label = {

                    Text(screen.title)
                }
            )
        }
    }
}