package com.amikom.sweetlife.ui.screen.rekomend

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RekomenScreen(
    viewModel: RekomenViewModel = hiltViewModel()
) {
    val rekomend by viewModel.foodRecommendations.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.error.observeAsState(null)

    LaunchedEffect(Unit) {
        viewModel.fetchRekomend()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            error != null -> Text(
                text = error ?: "Unknown error",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Center)
            )

            rekomend.isEmpty() -> Text(
                text = "No recommendations available",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Center)
            )

            else -> LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(rekomend) { item ->
                    RekomendItem(item = item)
                }
            }
        }
    }
}

@Composable
fun RekomendItem(item: FoodRecommendation) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = item.name ?: "Unknown food",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Calories: ${item.details?.calories ?: 0}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Portion: ${item.details?.portion ?: "Unknown"}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewRekomenScreen() {
    val mockData = listOf(
        FoodRecommendation(
            name = "Nasi Goreng",
            details = FoodDetails(
                calories = 300,
                portion = "1 porsi",
                type = "Makanan berat"
            ),
            image = Image(url = "https://www.example.com/nasi-goreng.png")
        ),
        FoodRecommendation(
            name = "Ayam Penyet",
            details = FoodDetails(
                calories = 450,
                portion = "1 porsi",
                type = "Makanan berat"
            ),
            image = Image(url = "https://www.example.com/ayam-penyet.png")
        )
    )

    Column {
        LazyColumn {
            items(mockData) { item ->
                RekomendItem(item = item)
            }
        }
    }
}
