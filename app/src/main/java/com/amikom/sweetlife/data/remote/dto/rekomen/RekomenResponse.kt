package com.amikom.sweetlife.data.remote.dto.rekomen

import com.google.gson.annotations.SerializedName

data class RekomenResponse(

    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("status")
    val status: Boolean = false
)

data class Data(

    @field:SerializedName("exerciseRecommendations")
    val exerciseRecommendations: ExerciseRecommendations? = null,

    @field:SerializedName("food_recommendation")
    val foodRecommendation: List<FoodRecommendation> = emptyList()
)

data class ExerciseRecommendations(
    @field:SerializedName("calories_burned")
    val caloriesBurned: Int = 0,

    @field:SerializedName("exercise_duration")
    val exerciseDuration: Int = 0,

    @field:SerializedName("exercise_list")
    val exerciseList: List<Exercise> = emptyList()
)

data class Exercise(
    @field:SerializedName("image")
    val image: String = "",

    @field:SerializedName("name")
    val name: String = "",

    @field:SerializedName("desc")
    val desc: String = ""
)

data class FoodRecommendation(
    @field:SerializedName("image")
    val imageUrl: String = "",

    @field:SerializedName("name")
    val name: String = "",

    @field:SerializedName("details")
    val details: Details = Details()
)

data class Details(
    @field:SerializedName("proteins")
    val proteins: String = "",

    @field:SerializedName("fat")
    val fat: String = "",

    @field:SerializedName("calories")
    val calories: String = "",

    @field:SerializedName("carbohydrate")
    val carbohydrate: String = ""
)