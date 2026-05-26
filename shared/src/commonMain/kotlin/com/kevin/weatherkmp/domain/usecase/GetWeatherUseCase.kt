package com.kevin.weatherkmp.domain.usecase

import com.kevin.weatherkmp.domain.repository.WeatherRepository

class GetWeatherUseCase(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(
        city: String
    ) = repository.getWeather(city)
}
