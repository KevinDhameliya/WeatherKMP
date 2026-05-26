package com.kevin.weatherkmp.presentation.weather

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kevin.weatherkmp.presentation.components.WeatherContent

@Composable
fun TestWeatherScreen() {

    val viewModel = remember {
        WeatherViewModel()
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    var city by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(20.dp)
    ) {

        OutlinedTextField(
            value = city,
            onValueChange = {
                city = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Enter City")
            }
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = {

                if (city.isNotBlank()) {
                    viewModel.getWeather(city)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Get Weather")
        }

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {

            when {

                state.isLoading -> {

                    CircularProgressIndicator()
                }

                state.error != null -> {

                    Text(
                        text = state.error!!
                    )
                }

                state.weather != null -> {

                    WeatherContent(
                        weather = state.weather!!
                    )
                }
            }
        }
    }
}