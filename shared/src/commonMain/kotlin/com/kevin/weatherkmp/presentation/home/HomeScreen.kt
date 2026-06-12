package com.kevin.weatherkmp.presentation.home

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
import com.kevin.weatherkmp.utils.Constants
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement
import com.kevin.weatherkmp.presentation.components.SearchChip
import com.kevin.weatherkmp.presentation.home.HomeViewModel

@Composable
fun HomeScreen() {

    val viewModel: HomeViewModel = koinViewModel()

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getWeather(Constants.DEFAULT_CITY)
    }

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
                .navigationBarsPadding()
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
                enabled = !state.isLoading,
                onClick = {

                    if (city.isNotBlank()) {
                        viewModel.onEvent(
                            HomeEvent.SearchCity(city)
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    if (state.isLoading)
                        "Loading..."
                    else
                        "Get Weather"
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            if (state.recentSearches.isNotEmpty()) {

                Text(
                    text = "Recent Searches",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    items(state.recentSearches) { city ->

                        SearchChip(
                            city = city,
                            onClick = {
                                viewModel.onEvent(
                                    HomeEvent.SearchCity(city)
                                )
                            }
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(30.dp)
                )
            }

            when {

                state.error != null -> {

                    ErrorContent(
                        message = state.error!!,
                        onRetry = {
                            viewModel.onEvent(
                                HomeEvent.Retry
                            )
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
