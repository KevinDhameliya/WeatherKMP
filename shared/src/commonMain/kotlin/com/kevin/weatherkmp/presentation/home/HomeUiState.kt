package com.kevin.weatherkmp.presentation.home

import com.kevin.weatherkmp.domain.model.Weather

data class HomeUiState(

    val isLoading: Boolean = false,

    val weather: Weather? = null,

    val error: String? = null,

    val searchedCity: String = "",

    val recentSearches: List<String> = emptyList()
)