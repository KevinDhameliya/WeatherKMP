package com.kevin.weatherkmp.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface SearchHistoryRepository {

    suspend fun saveCity(
        city: String
    )

    val cities: StateFlow<List<String>>
}