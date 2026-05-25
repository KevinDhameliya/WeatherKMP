package com.kevin.weatherkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform