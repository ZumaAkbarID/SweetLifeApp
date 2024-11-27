package com.amikom.sweetlife.ui.screen.rekomend

import com.google.gson.annotations.SerializedName

data class RekomenModel(
    @field:SerializedName("status")
    val status: String? = null,
    @field:SerializedName("data")
    val data: RekomendationData? = null
)

data class RekomendationData(
    @field:SerializedName("foodRecommendations")
    val foodRecommendations: List<FoodRecommendation>? = null,
    @field:SerializedName("exerciseRecommendations")
    val exerciseRecommendations: List<ExerciseRecommendation>? = null
)

data class FoodRecommendation(
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("details")
    val details: FoodDetails? = null,
    @field:SerializedName("image")
    val image: Image? = null
)

data class ExerciseRecommendation(
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("details")
    val details: ExerciseDetails? = null,
    @field:SerializedName("image")
    val image: Image? = null
)

data class FoodDetails(
    @field:SerializedName("calories")
    val calories: Int? = null,
    @field:SerializedName("portion")
    val portion: String? = null,
    @field:SerializedName("type")
    val type: String? = null
)

data class ExerciseDetails(
    @field:SerializedName("duration")
    val duration: String? = null,
    @field:SerializedName("caloriesBurn")
    val caloriesBurn: Int? = null,
    @field:SerializedName("totalExercises")
    val totalExercises: Int? = null
)

data class Image(
    @field:SerializedName("url")
    val url: String? = null,
)