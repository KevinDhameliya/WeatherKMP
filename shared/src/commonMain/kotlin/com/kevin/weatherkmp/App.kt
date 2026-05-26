package com.kevin.weatherkmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kevin.weatherkmp.presentation.weather.TestWeatherScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        TestWeatherScreen()
    }
}