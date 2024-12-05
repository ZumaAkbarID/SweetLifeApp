package com.amikom.sweetlife.ui.screen.rekomend

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.amikom.sweetlife.R
import com.amikom.sweetlife.data.remote.dto.rekomen.Details
import com.amikom.sweetlife.data.remote.dto.rekomen.Exercise
import com.amikom.sweetlife.data.remote.dto.rekomen.ExerciseRecommendations
import com.amikom.sweetlife.data.remote.dto.rekomen.FoodRecommendation
import com.amikom.sweetlife.domain.repository.RekomenRepository
import com.amikom.sweetlife.ui.component.BottomNavigationBar
import com.amikom.sweetlife.ui.component.getBottomNavButtons
import com.amikom.sweetlife.util.Constants

sealed class RecommendationItem {
    data class Food(val food: FoodRecommendation) : RecommendationItem()
    data class Exercise(val exercise: ExerciseRecommendations) : RecommendationItem()
}

@Composable
fun RekomenScreen(
    viewModel: RekomenViewModel = hiltViewModel(),
    navController: NavController
) {
    val selectedIndex = Constants.CURRENT_BOTTOM_BAR_PAGE_ID
    val buttons = getBottomNavButtons(selectedIndex, navController)

    val foodRecommendations by viewModel.foodRecommendations.observeAsState(emptyList())
    val exerciseRecommendations by viewModel.exerciseRecommendations.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.error.observeAsState(null)

    var selectedTabIndex by remember { mutableStateOf(0) }

    val tabTitles = listOf("Foods", "Exercises")

    Scaffold(
        bottomBar = {
            BottomNavigationBar(buttons = buttons)
        },
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(text = title) }
                    )
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    error != null -> Text(
                        text = error ?: "Unknown error",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    selectedTabIndex == 0 && foodRecommendations.isEmpty() -> Text(
                        text = "No food recommendations available",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    selectedTabIndex == 1 && exerciseRecommendations.isEmpty() -> Text(
                        text = "No exercise recommendations available",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    // Menampilkan data
                    else -> LazyColumn(modifier = Modifier.fillMaxSize()) {
                        if (selectedTabIndex == 0) {
                            items(foodRecommendations) { food ->
                                RekomendItemFood(food)
                            }
                        } else {
                            items(exerciseRecommendations) { exercise ->
                                RekomendItemExec(exercise)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun RekomendItemFood(item: FoodRecommendation) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, RoundedCornerShape(15.dp))
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Gambar makanan
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.secondary)
        ) {
            Image(
                painter = rememberAsyncImagePainter(item.imageUrl ?: ""),
                contentDescription = "Food Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Nama makanan
            Text(
                text = item.name ?: "Unknown food",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Detail makanan
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_notifications_none_24), // Ikon untuk kalori
                    contentDescription = "Calories",
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${item.details?.calories ?: 0} Kcal",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.width(16.dp))

                Icon(
                    painter = painterResource(id = R.drawable.baseline_notifications_none_24), // Ikon untuk berat/protein
                    contentDescription = "Weight",
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${item.details?.proteins ?: 0}gr",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun RekomendItemExec(item: ExerciseRecommendations) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, RoundedCornerShape(15.dp))
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        // Bagian atas: Ringkasan Kalori dan Durasi
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Calories burned: ${item.caloriesBurned}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "Duration: ${item.exerciseDuration}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Daftar latihan
        item.exerciseList?.forEach { exercise ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Gambar Latihan
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.secondary)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(exercise.image ?: ""),
                        contentDescription = "Exercise Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Detail Latihan
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = exercise.name ?: "Unknown Exercise",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = exercise.desc ?: "No description",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun RekomendItemFoodPreview() {
    RekomendItemFood(
        FoodRecommendation(
            imageUrl = "https://www.themealdb.com/images/media/meals/1548772327.jpg",
            name = "Beef and Mustard Pie",
            details = Details(
                calories = "200",
                proteins = "20"
            )
        )
    )
    ExerciseRecommendations(
        caloriesBurned = 200,
        exerciseDuration = 30,
        exerciseList = listOf(
            Exercise(
                image = "https://www.themealdb.com/images/media/meals/1548772327.jpg",
                name = "Jumping Jacks",
                desc = "Jumping jacks are a full-body workout that can target your arms, legs, and core."
            )
        )
    )
}