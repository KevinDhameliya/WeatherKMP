package com.kevin.weatherkmp.data.remote.api

import com.kevin.weatherkmp.data.model.WeatherDto
import io.ktor.client.call.body
import io.ktor.client.request.get

class WeatherApi {

    private val client = createHttpClient()

    suspend fun getWeather(
        city: String
    ): WeatherDto {

        return client
            .get(
                "https://api.weatherapi.com/v1/current.json?key=3261d27c0b21479e8b864128262605&q=$city"
            )
            .body()
    }
}
