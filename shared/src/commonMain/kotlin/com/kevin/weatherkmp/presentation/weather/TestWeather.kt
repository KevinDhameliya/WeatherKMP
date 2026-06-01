package com.kevin.weatherkmp.presentation.weather

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kevin.weatherkmp.presentation.components.ErrorContent
import com.kevin.weatherkmp.presentation.components.WeatherContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TestWeatherScreen() {

    val viewModel: WeatherViewModel = koinViewModel()

    val state by viewModel.state.collectAsStateWithLifecycle()

    var city by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
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
                },
                singleLine = true
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

            when {

                state.error != null -> {

                    ErrorContent(
                        message = state.error!!,
                        onRetry = {
                            viewModel.retry()
                        }
                    )
                }

                state.weather != null -> {

                    WeatherContent(
                        weather = state.weather!!
                    )
                }
            }
        }

        if (state.isLoading) {

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                CircularProgressIndicator()
            }
        }
    }
}
