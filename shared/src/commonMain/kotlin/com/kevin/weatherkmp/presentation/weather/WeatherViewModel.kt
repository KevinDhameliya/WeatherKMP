package com.kevin.weatherkmp.presentation.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevin.weatherkmp.domain.usecase.GetWeatherUseCase
import com.kevin.weatherkmp.presentation.state.WeatherUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.kevin.weatherkmp.location.LocationManager

class WeatherViewModel(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val locationManager: LocationManager
) : ViewModel() {

    private val _state = MutableStateFlow(
        WeatherUiState()
    )

    val state: StateFlow<WeatherUiState> =
        _state.asStateFlow()

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

                _state.value = _state.value.copy(
                    isLoading = false,
                    weather = result
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

    fun loadCurrentLocationWeather() {

        viewModelScope.launch {

            val city =
                locationManager.getCurrentCity()

            getWeather(city)
        }
    }
}