package com.kevin.weatherkmp.di

import com.kevin.weatherkmp.data.remote.api.WeatherApi
import com.kevin.weatherkmp.data.repository.WeatherRepositoryImpl
import com.kevin.weatherkmp.domain.repository.WeatherRepository
import com.kevin.weatherkmp.domain.usecase.GetWeatherUseCase
import com.kevin.weatherkmp.location.LocationManager
import com.kevin.weatherkmp.presentation.weather.WeatherViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        WeatherApi()
    }

    single {
        LocationManager()
    }

    single<WeatherRepository> {
        WeatherRepositoryImpl(get())
    }

    single {
        GetWeatherUseCase(get())
    }

    viewModel {
        WeatherViewModel(get(), get())
    }
}