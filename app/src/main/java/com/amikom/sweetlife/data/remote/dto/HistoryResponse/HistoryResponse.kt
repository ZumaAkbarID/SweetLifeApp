package com.amikom.sweetlife.data.remote.dto.HistoryResponse

import com.google.gson.annotations.SerializedName

// HistoryResponse class
data class HistoryResponse(
    @SerializedName("status") val status: String = "",
    @SerializedName("data") val data: HistoryData? = null
)

// Data class with pagination and food history
data class HistoryData(
    @SerializedName("pagination") val pagination: Pagination = Pagination(),
    @SerializedName("food_history") val foodHistory: List<FoodHistory> = emptyList()
)

// Pagination class
data class Pagination(
    @SerializedName("currentPage") val currentPage: Int = 1,
    @SerializedName("totalPages") val totalPages: Int = 0,
    @SerializedName("totalItems") val totalItems: Int = 0,
    @SerializedName("itemsPerPage") val itemsPerPage: Int = 10
)

// FoodHistory class
data class FoodHistory(
    @SerializedName("date") val date: String = "",
    @SerializedName("totalCalories") val totalCalories: Int = 0,
    @SerializedName("entries") val entries: List<FoodLog> = emptyList()
)

// Entry class
data class FoodLog(
    @SerializedName("id") val id: String = "",
    @SerializedName("food_name") val foodName: String = "",
    @SerializedName("calories") val calories: Int = 0,
    @SerializedName("time") val time: String = "",
    @SerializedName("totalUnits") val totalUnits: Int? = null
)