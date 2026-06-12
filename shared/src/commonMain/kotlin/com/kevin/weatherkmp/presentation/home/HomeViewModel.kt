package com.kevin.weatherkmp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevin.weatherkmp.domain.repository.SearchHistoryRepository
import com.kevin.weatherkmp.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class HomeViewModel(

    private val getWeatherUseCase:
    GetWeatherUseCase,

    private val searchHistoryRepository:
    SearchHistoryRepository

) : ViewModel() {

    private val _state = MutableStateFlow(
        HomeUiState()
    )

    val state: StateFlow<HomeUiState> =
        _state.asStateFlow()

    init {

        viewModelScope.launch {

            searchHistoryRepository.cities.collect {

                _state.value = _state.value.copy(
                    recentSearches = it
                )
            }
        }
    }

    fun getWeather(
        city: String
    ) {

        viewModelScope.launch {

            _state.value = _state.value.copy(
                isLoading = true,
                error = null,
                searchedCity = city
            )

            try {

                val result = getWeatherUseCase(city)

                searchHistoryRepository.saveCity(city)

                _state.value = _state.value.copy(
                    isLoading = false,
                    weather = result,
                )

            } catch (e: Exception) {

                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun retry() {

        if (_state.value.searchedCity.isNotBlank()) {

            getWeather(_state.value.searchedCity)
        }
    }

    fun onEvent(
        event: HomeEvent
    ) {

        when (event) {

            is HomeEvent.SearchCity -> {

                getWeather(event.city)
            }

            HomeEvent.Retry -> {

                retry()
            }
        }
    }

//    fun loadCurrentLocationWeather() {
//
//        viewModelScope.launch {
//
//            val city =
//                locationManager.getCurrentCity()
//
//            getWeather(city)
//        }
//    }
}