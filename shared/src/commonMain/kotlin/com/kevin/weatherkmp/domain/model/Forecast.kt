package com.kevin.weatherkmp.domain.model

data class Forecast(

    val date: String,

    val temperature: Double,

    val condition: String,

    val iconUrl: String
)