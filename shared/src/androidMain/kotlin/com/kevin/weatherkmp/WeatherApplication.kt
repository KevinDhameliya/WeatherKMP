package com.kevin.weatherkmp

import android.app.Application
import com.kevin.weatherkmp.di.initKoin
import org.koin.android.ext.koin.androidContext

class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@WeatherApplication)
        }
    }
}
