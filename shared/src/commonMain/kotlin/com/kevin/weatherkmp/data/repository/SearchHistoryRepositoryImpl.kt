package com.kevin.weatherkmp.data.repository

import com.kevin.weatherkmp.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchHistoryRepositoryImpl :
    SearchHistoryRepository {

    private val _cities =
        MutableStateFlow<List<String>>(
            emptyList()
        )

    override val cities: StateFlow<List<String>>
            = _cities.asStateFlow()

    override suspend fun saveCity(
        city: String
    ) {

        val currentList =
            _cities.value.toMutableList()

        currentList.remove(city)

        currentList.add(
            0,
            city
        )

        _cities.value =
            currentList
    }
}