package com.kevin.weatherkmp.di

import com.kevin.weatherkmp.data.local.SearchHistoryLocalDataSource
import com.kevin.weatherkmp.data.local.SearchHistoryLocalDataSourceImpl
import com.kevin.weatherkmp.data.remote.api.WeatherApi
import com.kevin.weatherkmp.data.repository.SearchHistoryRepositoryImpl
import com.kevin.weatherkmp.data.repository.WeatherRepositoryImpl
import com.kevin.weatherkmp.domain.repository.SearchHistoryRepository
import com.kevin.weatherkmp.domain.repository.WeatherRepository
import com.kevin.weatherkmp.domain.usecase.GetCurrentLocationWeatherUseCase
import com.kevin.weatherkmp.domain.usecase.GetWeatherUseCase
import com.kevin.weatherkmp.location.LocationPermissionController
import com.kevin.weatherkmp.location.LocationService
import com.kevin.weatherkmp.presentation.history.HistoryViewModel
import com.kevin.weatherkmp.presentation.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        WeatherApi()
    }

    single {
        LocationPermissionController()
    }

    single {
        LocationService()
    }

    single<LocationRepository> {
        LocationRepositoryImpl(get())
    }

    single<WeatherRepository> {
        WeatherRepositoryImpl(get())
    }

    single {
        GetWeatherUseCase(get())
    }

    single {
        GetCurrentLocationWeatherUseCase(
            get(),
            get()
        )
    }

    single<SearchHistoryLocalDataSource> {
        SearchHistoryLocalDataSourceImpl(get())
    }

    single<SearchHistoryRepository> {
        SearchHistoryRepositoryImpl(get())
    }

    viewModel {
        HomeViewModel(
            get(),
            get(),
            get()
        )
    }

    viewModel {

        HistoryViewModel(
            get()
        )
    }
}
