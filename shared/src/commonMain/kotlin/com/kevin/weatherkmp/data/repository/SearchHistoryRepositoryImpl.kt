package com.kevin.weatherkmp.data.repository

import com.kevin.weatherkmp.data.local.SearchHistoryLocalDataSource
import com.kevin.weatherkmp.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchHistoryRepositoryImpl(
    private val localDataSource: SearchHistoryLocalDataSource
) : SearchHistoryRepository {

    private val _cities = MutableStateFlow(
        localDataSource.loadCities()
    )

    override val cities: StateFlow<List<String>>
        get() = _cities.asStateFlow()

    override suspend fun saveCity(
        city: String
    ) {
        val trimmedCity = city.trim()
        if (trimmedCity.isBlank()) return

        updateCities { currentList ->
            val updatedList = currentList.toMutableList()
            updatedList.remove(trimmedCity)
            updatedList.add(0, trimmedCity)
            updatedList.take(MAX_HISTORY_SIZE)
        }
    }

    override suspend fun deleteCity(
        city: String
    ) {
        updateCities { currentList ->
            currentList.filterNot { it == city }
        }
    }

    override suspend fun clearAll() {
        updateCities { emptyList() }
    }

    private fun updateCities(
        transform: (List<String>) -> List<String>
    ) {
        val updatedList = transform(_cities.value)
        localDataSource.saveCities(updatedList)
        _cities.value = updatedList
    }

    companion object {
        private const val MAX_HISTORY_SIZE = 10
    }
}
