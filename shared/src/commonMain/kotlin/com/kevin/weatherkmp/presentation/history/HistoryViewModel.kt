package com.kevin.weatherkmp.presentation.history

import androidx.lifecycle.ViewModel
import com.kevin.weatherkmp.domain.repository.SearchHistoryRepository

class HistoryViewModel(

    repository:
    SearchHistoryRepository

) : ViewModel() {

    val cities =
        repository.cities
}