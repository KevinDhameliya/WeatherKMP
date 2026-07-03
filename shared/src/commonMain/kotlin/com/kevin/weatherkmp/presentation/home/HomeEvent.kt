package com.kevin.weatherkmp.presentation.home

sealed interface HomeEvent {

    data class SearchCity(
        val city: String
    ) : HomeEvent

    data object LoadCurrentLocation : HomeEvent

    data object Retry : HomeEvent
}