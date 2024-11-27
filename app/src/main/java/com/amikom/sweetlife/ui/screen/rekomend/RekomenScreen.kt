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
    val rekomend by viewModel.rekomend.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.error.observeAsState(null)

    LaunchedEffect(Unit) {
        viewModel.fetchRekomend()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            error != null -> Text(
                text = error ?: "",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Center)
            )

            rekomend.isEmpty() -> Text(
                text = "No data",
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
fun RekomendItem(item: RekomendationData) {
    Text(
        text = item.foodRecommendations?.get(0)?.name ?: "",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(16.dp)
    )
}
@Preview
@Composable
fun PreviewRekomenScreen() {
    Column {
        RekomenScreen(

        )
    }
}