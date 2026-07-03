package com.kevin.weatherkmp

import androidx.compose.ui.window.ComposeUIViewController
import com.kevin.weatherkmp.di.initKoin
import org.koin.mp.KoinPlatformTools

fun MainViewController() = ComposeUIViewController {
    if (KoinPlatformTools.defaultContext().getOrNull() == null) {
        initKoin()
    }
    App()
}