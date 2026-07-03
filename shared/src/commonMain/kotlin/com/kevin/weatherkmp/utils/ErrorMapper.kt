package com.kevin.weatherkmp.utils

import com.kevin.weatherkmp.domain.exception.LocationException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ResponseException

fun Throwable.toUserMessage(): String {
    return when (this) {
        is LocationException -> message
        is HttpRequestTimeoutException,
        is ResponseException -> {
            "No internet connection. Please check your network and try again."
        }

        else -> {
            val networkError = message?.contains("host", ignoreCase = true) == true ||
                message?.contains("network", ignoreCase = true) == true ||
                message?.contains("connection", ignoreCase = true) == true

            if (networkError) {
                "No internet connection. Please check your network and try again."
            } else {
                message ?: "Something went wrong. Please try again."
            }
        }
    }
}
