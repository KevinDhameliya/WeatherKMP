package com.kevin.weatherkmp.domain.model

data class Weather(
    val city: String,

    val country: String,

    val temperature: Double,

    val humidity: Int,

    val windSpeed: Double,

    val condition: String,

    val iconUrl: String
)
