package com.kevin.weatherkmp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDto(
    @SerialName("location")
    val location: LocationDto,

    @SerialName("current")
    val current: CurrentWeatherDto,

    @SerialName("forecast")
    val forecast: ForecastDto
)

@Serializable
data class LocationDto(

    @SerialName("name")
    val name: String,

    @SerialName("country")
    val country: String
)

@Serializable
data class CurrentWeatherDto(

    @SerialName("temp_c")
    val temperature: Double,

    @SerialName("humidity")
    val humidity: Int,

    @SerialName("condition")
    val condition: ConditionDto,

    @SerialName("wind_kph")
    val windSpeed: Double

)

@Serializable
data class ConditionDto(

    @SerialName("text")
    val text: String,

    @SerialName("icon")
    val icon: String
)