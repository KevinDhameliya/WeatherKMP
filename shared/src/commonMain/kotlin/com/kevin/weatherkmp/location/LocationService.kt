package com.kevin.weatherkmp.location

expect class LocationService() {

    suspend fun getCurrentCity(): Result<String>
}
