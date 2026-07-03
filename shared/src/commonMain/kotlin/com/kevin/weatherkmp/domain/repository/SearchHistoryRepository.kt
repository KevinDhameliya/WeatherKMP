package com.kevin.weatherkmp.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface SearchHistoryRepository {

    suspend fun saveCity(
        city: String
    )

    suspend fun deleteCity(
        city: String
    )

    suspend fun clearAll()

    val cities: StateFlow<List<String>>
}