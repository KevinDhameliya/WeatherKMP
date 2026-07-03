package com.kevin.weatherkmp.location

import com.kevin.weatherkmp.utils.Constants

actual class LocationPermissionController actual constructor() {

    actual fun isLocationPermissionGranted(): Boolean = true

    actual suspend fun requestLocationPermission(): Boolean = true
}

actual class LocationService actual constructor() {

    actual suspend fun getCurrentCity(): Result<String> {
        return Result.success(Constants.DEFAULT_CITY)
    }
}
