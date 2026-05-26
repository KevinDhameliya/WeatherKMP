package com.kevin.weatherkmp.domain.repository

import com.kevin.weatherkmp.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeather(
        city: String
    ): Weather
}
