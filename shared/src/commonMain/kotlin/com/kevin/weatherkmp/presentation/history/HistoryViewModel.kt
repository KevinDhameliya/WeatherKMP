package com.kevin.weatherkmp.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevin.weatherkmp.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val repository: SearchHistoryRepository
) : ViewModel() {

    val cities: StateFlow<List<String>> =
        repository.cities

    fun deleteCity(
        city: String
    ) {
        viewModelScope.launch {
            repository.deleteCity(city)
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            repository.clearAll()
        }
    }
}
