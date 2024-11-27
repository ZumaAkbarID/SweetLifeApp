package com.amikom.sweetlife.ui.screen.rekomend

import kotlinx.coroutines.delay
import javax.inject.Inject

class RekomenRepository @Inject constructor() {
    suspend fun fetchRekomend(): RekomenModel {
        delay(2000)
        return RekomenModel(
            status = "success",
            data = RekomendationData(
                foodRecommendations = listOf(
                    FoodRecommendation(
                        name = "Nasi Goreng",
                        details = FoodDetails(
                            calories = 100,
                            portion = "1 porsi",
                            type = "Makanan berat"
                        ),
                        image = Image(
                            url = "https://www.example.com/nasi-goreng.png"
                        )
                    ),
                    FoodRecommendation(
                        name = "Ayam Penyet",
                        details = FoodDetails(
                            calories = 200,
                            portion = "1 porsi",
                            type = "Makanan berat"
                        ),
                        image = Image(
                            url = "https://www.example.com/ayam-penyet.png"
                        )
                    )
                ),
                exerciseRecommendations = listOf(
                    ExerciseRecommendation(
                        name = "Lari",
                        details = ExerciseDetails(
                            duration = "30 menit",
                            caloriesBurn = 200,
                            totalExercises = 1
                        ),
                        image = Image(
                            url = "https://www.example.com/lari.png"
                        )
                    ),
                    ExerciseRecommendation(
                        name = "Push Up",
                        details = ExerciseDetails(
                            duration = "10 menit",
                            caloriesBurn = 100,
                            totalExercises = 1
                        ),
                        image = Image(
                            url = "https://www.example.com/push-up.png"
                        )
                    )
                )
            )
        )
    }
}