package com.kevin.weatherkmp.data.repository

import com.kevin.weatherkmp.data.mapper.toDomain
import com.kevin.weatherkmp.data.remote.api.WeatherApi
import com.kevin.weatherkmp.domain.model.Weather
import com.kevin.weatherkmp.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val api: WeatherApi
) : WeatherRepository {

    override suspend fun getWeather(
        city: String
    ): Weather {

        return api.getWeather(city).toDomain()
    }
}