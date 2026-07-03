package com.kevin.weatherkmp.data.local

interface SearchHistoryLocalDataSource {

    fun loadCities(): List<String>

    fun saveCities(cities: List<String>)
}
