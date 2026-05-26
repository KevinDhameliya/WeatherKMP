package com.kevin.weatherkmp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight

@Composable
fun WeatherInfoItem(
    title: String,
    value: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}