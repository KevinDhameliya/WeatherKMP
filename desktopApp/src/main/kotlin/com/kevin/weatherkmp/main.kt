package com.kevin.weatherkmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.kevin.weatherkmp.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "WeatherKMP",
        ) {
            App()
        }
    }
}