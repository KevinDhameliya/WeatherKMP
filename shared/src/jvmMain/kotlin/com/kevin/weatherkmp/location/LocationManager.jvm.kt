package com.kevin.weatherkmp.location

actual class LocationManager actual constructor() {

    actual suspend fun getCurrentCity(): String {

        return "Ahmedabad"
    }
}