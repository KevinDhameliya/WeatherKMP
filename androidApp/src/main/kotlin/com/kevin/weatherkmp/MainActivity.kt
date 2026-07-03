package com.kevin.weatherkmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kevin.weatherkmp.location.LocationPermissionController
import org.koin.android.ext.android.getKoin

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getKoin().get<LocationPermissionController>().bind(this)

        setContent {
            App()
        }
    }
}
