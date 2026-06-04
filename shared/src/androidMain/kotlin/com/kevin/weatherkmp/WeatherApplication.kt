package com.kevin.weatherkmp

import android.app.Application
import com.kevin.weatherkmp.di.initKoin

class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }
}
