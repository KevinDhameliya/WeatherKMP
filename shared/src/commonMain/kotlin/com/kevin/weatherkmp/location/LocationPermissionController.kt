package com.kevin.weatherkmp.location

expect class LocationPermissionController() {

    fun isLocationPermissionGranted(): Boolean

    suspend fun requestLocationPermission(): Boolean
}
