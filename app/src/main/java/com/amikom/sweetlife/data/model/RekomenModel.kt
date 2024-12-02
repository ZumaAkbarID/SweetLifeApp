package com.amikom.sweetlife.data.model

data class RekomenModel(
    val data: RekomenData? = null,
    val status: Boolean = false
)

data class RekomenData(
    val exerciseRecommendations: ExerciseRecommendations? = null,
    val foodRecommendation: List<FoodRecommendation>? = emptyList()
)

data class ExerciseRecommendations(
    val caloriesBurned: Int = 0,
    val exerciseDuration: Int = 0,
    val exerciseList: List<Exercise>? = emptyList()
)

data class Exercise(
    val image: String = "",
    val name: String = "",
    val desc: String = ""
)

data class FoodRecommendation(
    val imageUrl: String = "",
    val name: String = "",
    val proteins: String = "",
    val fat: String = "",
    val calories: String = "",
    val carbohydrate: String = ""
)
