package com.kevin.weatherkmp.location

expect class LocationManager() {

    suspend fun getCurrentCity(): String
}
