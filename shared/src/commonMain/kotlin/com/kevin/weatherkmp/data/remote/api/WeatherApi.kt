package com.kevin.weatherkmp.data.remote.api

import com.kevin.weatherkmp.data.model.WeatherDto
import com.kevin.weatherkmp.utils.Constants.API_KEY
import io.ktor.client.call.body
import io.ktor.client.request.get

class WeatherApi {

    private val client = createHttpClient()

    suspend fun getWeather(city: String): WeatherDto {

        return client.get(
            "https://api.weatherapi.com/v1/forecast.json?key=$API_KEY&q=$city&days=7"
        ).body()
    }
}
