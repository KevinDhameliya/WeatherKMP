package com.kevin.weatherkmp.presentation.components

import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SearchChip(
    city: String,
    onClick: () -> Unit
) {

    AssistChip(
        onClick = onClick,
        label = {
            Text(city)
        },
        colors = AssistChipDefaults.assistChipColors()
    )
}