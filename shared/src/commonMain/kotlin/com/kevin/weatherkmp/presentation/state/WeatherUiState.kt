package com.kevin.weatherkmp.presentation.state

import com.kevin.weatherkmp.domain.model.Weather

data class WeatherUiState(

    val isLoading: Boolean = false,

    val weather: Weather? = null,

    val error: String? = null,

    val searchedCity: String = ""
)