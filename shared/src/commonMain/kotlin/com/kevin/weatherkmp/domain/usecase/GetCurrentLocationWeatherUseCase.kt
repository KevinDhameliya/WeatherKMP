package com.kevin.weatherkmp.domain.usecase

import com.kevin.weatherkmp.domain.exception.LocationException
import com.kevin.weatherkmp.domain.model.Weather

class GetCurrentLocationWeatherUseCase(
    private val locationRepository: LocationRepository,
    private val getWeatherUseCase: GetWeatherUseCase
) {

    suspend operator fun invoke(): Weather {
        val city = locationRepository.getCurrentCity().getOrElse { error ->
            throw error as? LocationException
                ?: LocationException.UnknownLocation
        }

        return getWeatherUseCase(city)
    }
}
