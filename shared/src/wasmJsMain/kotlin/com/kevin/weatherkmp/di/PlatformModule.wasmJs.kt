package com.kevin.weatherkmp.di

import com.russhwolf.settings.MapSettings
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<Settings> {
        MapSettings()
    }
}
