package com.amikom.sweetlife.ui.screen.rekomend

import androidx.compose.foundation.Image
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
import com.amikom.sweetlife.data.remote.dto.rekomen.Details
import com.amikom.sweetlife.data.remote.dto.rekomen.Exercise
import com.amikom.sweetlife.data.remote.dto.rekomen.ExerciseRecommendations
import com.amikom.sweetlife.data.remote.dto.rekomen.FoodRecommendation
import com.amikom.sweetlife.domain.repository.RekomenRepository

@Composable
fun RekomenScreen(
    viewModel: RekomenViewModel = hiltViewModel()
) {
    // Observing the LiveData from the ViewModel
    val rekomend by viewModel.foodRecommendations.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.error.observeAsState(null)

    // Triggering the fetchRekomend function when the screen is first launched
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
                    RekomendItemFood(item = item)
                }
            }
        }
    }
}

@Composable
fun RekomendItemFood(item: FoodRecommendation) {
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
            text = "Proteins: ${item.details?.proteins ?: 0}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Carb: ${item.details?.carbohydrate ?: 0}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun RekomenItemExercise(item: ExerciseRecommendations) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Calories burned: ${item.caloriesBurned}",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Duration: ${item.exerciseDuration}",
            style = MaterialTheme.typography.bodyMedium
        )
        item.exerciseList?.forEach { exercise ->
            Text(
                text = exercise.name,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RekomenScreenPreview() {
    val foodRecommendation = FoodRecommendation(
        imageUrl = 0, // Gunakan image resource atau URL sesuai kebutuhan
        name = "Apple",
        details = Details(
            calories = "100",
            proteins = "10",
            carbohydrate = "20"
        )
    )

    val exerciseRecommendation = ExerciseRecommendations(
        caloriesBurned = 100,
        exerciseDuration = 30,
        exerciseList = listOf(
            Exercise(
                image = 0, // Gunakan image resource sesuai kebutuhan
                name = "Running",
                desc = "Running for 30 minutes"
            )
        )
    )
}
