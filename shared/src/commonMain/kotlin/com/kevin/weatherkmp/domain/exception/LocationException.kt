package com.kevin.weatherkmp.domain.exception

sealed class LocationException(
    override val message: String
) : Exception(message) {

    data object PermissionDenied : LocationException(
        "Location permission denied. Please enable it in settings to use current location."
    )

    data object GpsDisabled : LocationException(
        "Location services are disabled. Please turn on GPS and try again."
    )

    data object Timeout : LocationException(
        "Unable to determine your location in time. Please try again."
    )

    data object UnknownLocation : LocationException(
        "Unable to determine your city from your location. Please search manually."
    )
}
