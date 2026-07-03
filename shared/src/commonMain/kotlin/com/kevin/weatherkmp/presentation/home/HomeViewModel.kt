package com.kevin.weatherkmp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevin.weatherkmp.domain.repository.SearchHistoryRepository
import com.kevin.weatherkmp.domain.usecase.GetCurrentLocationWeatherUseCase
import com.kevin.weatherkmp.domain.usecase.GetWeatherUseCase
import com.kevin.weatherkmp.utils.toUserMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(

    private val getWeatherUseCase:
    GetWeatherUseCase,

    private val getCurrentLocationWeatherUseCase:
    GetCurrentLocationWeatherUseCase,

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

        if (_state.value.isLoading) return

        viewModelScope.launch {
            fetchWeatherForCity(city)
        }
    }

    fun loadCurrentLocationWeather() {

        if (_state.value.isLoading) return

        viewModelScope.launch {

            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )

            try {

                val weather = getCurrentLocationWeatherUseCase()

                searchHistoryRepository.saveCity(weather.city)

                _state.value = _state.value.copy(
                    isLoading = false,
                    weather = weather,
                    searchedCity = weather.city
                )

            } catch (error: Exception) {

                _state.value = _state.value.copy(
                    isLoading = false,
                    error = error.toUserMessage()
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

            HomeEvent.LoadCurrentLocation -> {

                loadCurrentLocationWeather()
            }

            HomeEvent.Retry -> {

                retry()
            }
        }
    }

    private suspend fun fetchWeatherForCity(
        city: String
    ) {

        _state.value = _state.value.copy(
            isLoading = true,
            error = null,
            searchedCity = city
        )

        try {

            val result = getWeatherUseCase(city)

            searchHistoryRepository.saveCity(result.city)

            _state.value = _state.value.copy(
                isLoading = false,
                weather = result,
            )

        } catch (error: Exception) {

            _state.value = _state.value.copy(
                isLoading = false,
                error = error.toUserMessage()
            )
        }
    }
}
