package com.kevin.weatherkmp.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager as AndroidLocationManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.kevin.weatherkmp.domain.exception.LocationException
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

actual class LocationPermissionController actual constructor() {

    private var activity: ComponentActivity? = null
    private var permissionLauncher: ActivityResultLauncher<Array<String>>? = null
    private var pendingContinuation: CancellableContinuation<Boolean>? = null

    fun bind(
        activity: ComponentActivity
    ) {
        this.activity = activity
        permissionLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            pendingContinuation?.resume(granted)
            pendingContinuation = null
        }
    }

    actual fun isLocationPermissionGranted(): Boolean {
        val context = activity ?: return false

        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
    }

    actual suspend fun requestLocationPermission(): Boolean {
        if (isLocationPermissionGranted()) {
            return true
        }

        val launcher = permissionLauncher
            ?: return false

        return suspendCancellableCoroutine { continuation ->
            pendingContinuation = continuation
            launcher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )

            continuation.invokeOnCancellation {
                pendingContinuation = null
            }
        }
    }
}

actual class LocationService actual constructor() : KoinComponent {

    private val context: Context by inject()
    private val permissionController: LocationPermissionController by inject()

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    actual suspend fun getCurrentCity(): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                if (!permissionController.isLocationPermissionGranted()) {
                    val granted = permissionController.requestLocationPermission()
                    if (!granted) {
                        return@withContext Result.failure(
                            LocationException.PermissionDenied
                        )
                    }
                }

                if (!isGpsEnabled()) {
                    return@withContext Result.failure(
                        LocationException.GpsDisabled
                    )
                }

                val location = withTimeout(LOCATION_TIMEOUT_MS) {
                    requestCurrentLocation()
                }

                val city = reverseGeocode(
                    latitude = location.latitude,
                    longitude = location.longitude
                ) ?: return@withContext Result.failure(
                    LocationException.UnknownLocation
                )

                Result.success(city)
            } catch (_: kotlinx.coroutines.TimeoutCancellationException) {
                Result.failure(LocationException.Timeout)
            } catch (error: LocationException) {
                Result.failure(error)
            } catch (_: Exception) {
                Result.failure(LocationException.UnknownLocation)
            }
        }
    }

    private fun isGpsEnabled(): Boolean {
        val locationManager = context.getSystemService(
            Context.LOCATION_SERVICE
        ) as AndroidLocationManager

        return locationManager.isProviderEnabled(
            AndroidLocationManager.GPS_PROVIDER
        ) || locationManager.isProviderEnabled(
            AndroidLocationManager.NETWORK_PROVIDER
        )
    }

    private suspend fun requestCurrentLocation() =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                null
            ).addOnSuccessListener { location ->
                if (location != null) {
                    continuation.resume(location)
                } else {
                    continuation.resumeWithException(
                        LocationException.UnknownLocation
                    )
                }
            }.addOnFailureListener { error ->
                continuation.resumeWithException(error)
            }
        }

    private suspend fun reverseGeocode(
        latitude: Double,
        longitude: Double
    ): String? {
        if (!Geocoder.isPresent()) {
            return null
        }

        val geocoder = Geocoder(context, Locale.getDefault())

        val addresses = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            suspendCancellableCoroutine { continuation ->
                geocoder.getFromLocation(
                    latitude,
                    longitude,
                    1
                ) { result ->
                    continuation.resume(result)
                }
            }
        } else {
            @Suppress("DEPRECATION")
            geocoder.getFromLocation(latitude, longitude, 1)
        }

        val address = addresses?.firstOrNull()
            ?: return null

        return address.locality
            ?: address.subAdminArea
            ?: address.adminArea
    }

    companion object {
        private const val LOCATION_TIMEOUT_MS = 15_000L
    }
}
