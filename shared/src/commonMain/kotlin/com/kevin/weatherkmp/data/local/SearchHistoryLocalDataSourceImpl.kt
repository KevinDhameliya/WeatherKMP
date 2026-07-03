package com.kevin.weatherkmp.data.local

import com.russhwolf.settings.Settings
import kotlinx.serialization.json.Json

class SearchHistoryLocalDataSourceImpl(
    private val settings: Settings
) : SearchHistoryLocalDataSource {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    override fun loadCities(): List<String> {
        val stored = settings.getStringOrNull(KEY)
            ?: return emptyList()

        return runCatching {
            json.decodeFromString<List<String>>(stored)
        }.getOrDefault(emptyList())
    }

    override fun saveCities(cities: List<String>) {
        settings.putString(
            KEY,
            json.encodeToString(cities)
        )
    }

    companion object {
        private const val KEY = "search_history_cities"
    }
}
