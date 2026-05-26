package com.kevin.weatherkmp.presentation.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevin.weatherkmp.data.remote.api.WeatherApi
import com.kevin.weatherkmp.data.repository.WeatherRepositoryImpl
import com.kevin.weatherkmp.domain.usecase.GetWeatherUseCase
import com.kevin.weatherkmp.presentation.state.WeatherUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val useCase = GetWeatherUseCase(
        WeatherRepositoryImpl(
            WeatherApi()
        )
    )

    private val _state = MutableStateFlow(
        WeatherUiState()
    )

    val state: StateFlow<WeatherUiState> =
        _state.asStateFlow()

    fun getWeather(
        city: String
    ) {

        viewModelScope.launch {

            _state.value = WeatherUiState(
                isLoading = true
            )

            try {

                val result = useCase(city)

                _state.value = WeatherUiState(
                    weather = result
                )

            } catch (e: Exception) {

                _state.value = WeatherUiState(
                    error = e.message
                )
            }
        }
    }
}