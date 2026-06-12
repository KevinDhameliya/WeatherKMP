package com.kevin.weatherkmp.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.ktor.websocket.Frame

@Composable
fun SearchSection(
    city: String,
    isLoading: Boolean,
    onCityChange: (String) -> Unit,
    onSearchClick: () -> Unit
) {

    OutlinedTextField(
        value = city,
        onValueChange = onCityChange,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Frame.Text("Enter City")
        },
        singleLine = true
    )

    Spacer(
        modifier = Modifier.height(16.dp)
    )

    Button(
        enabled = !isLoading,
        onClick = onSearchClick,
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            if (isLoading)
                "Loading..."
            else
                "Get Weather"
        )
    }
}