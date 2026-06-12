package com.kevin.weatherkmp.presentation.searchhistory

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kevin.weatherkmp.presentation.components.HistoryItem
import com.kevin.weatherkmp.presentation.history.HistoryViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchHistoryScreen() {

    val viewModel:
            HistoryViewModel =
        koinViewModel()

    val cities by
    viewModel.cities.collectAsState()

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)

    ) {

        Text(

            text = "Search History",

            style = MaterialTheme.typography.headlineMedium

        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        LazyColumn {

            items(cities) {

                HistoryItem(

                    city = it,

                    onClick = {

                    }

                )
            }
        }
    }
}