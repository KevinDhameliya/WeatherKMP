package com.kevin.weatherkmp.di

import org.koin.core.context.startKoin

fun initKoin() {

    startKoin {
        modules(appModule)
    }
}