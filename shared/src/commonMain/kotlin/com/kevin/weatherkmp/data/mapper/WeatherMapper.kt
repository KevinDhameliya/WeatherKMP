package com.kevin.weatherkmp.data.mapper

import com.kevin.weatherkmp.data.model.WeatherDto
import com.kevin.weatherkmp.domain.model.Forecast
import com.kevin.weatherkmp.domain.model.Weather

fun WeatherDto.toDomain(): Weather {

    return Weather(
        city = location.name,
        country = location.country,
        temperature = current.temperature,
        humidity = current.humidity,
        windSpeed = current.windSpeed,
        condition = current.condition.text,
        iconUrl = "https:${current.condition.icon}",
        forecast = forecast.forecastDays.map {

            Forecast(
                date = it.date,
                temperature = it.day.avgTemp,
                condition = it.day.condition.text,
                iconUrl = "https:${it.day.condition.icon}"
            )
        }
    )
}