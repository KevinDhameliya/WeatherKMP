package com.kevin.weatherkmp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastDto(

    @SerialName("forecastday")
    val forecastDays: List<ForecastDayDto>
)

@Serializable
data class ForecastDayDto(

    @SerialName("date")
    val date: String,

    @SerialName("day")
    val day: DayDto
)

@Serializable
data class DayDto(

    @SerialName("avgtemp_c")
    val avgTemp: Double,

    @SerialName("condition")
    val condition: ConditionDto
)