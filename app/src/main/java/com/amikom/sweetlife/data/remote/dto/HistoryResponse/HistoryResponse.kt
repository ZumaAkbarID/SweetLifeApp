package com.amikom.sweetlife.data.remote.dto.HistoryResponse


import com.google.gson.annotations.SerializedName

data class HistoryResponse(
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: HistoryData,
    @SerializedName("food_logs") val foodLogs: List<FoodLog>? = null
)

// Data class with pagination and food history
data class HistoryData(
    @SerializedName("pagination") val pagination: Pagination,

)

// Pagination class
data class Pagination(
    @SerializedName("currentPage") val currentPage: Int = 1,
    @SerializedName("totalPages") val totalPages: Int = 0,
    @SerializedName("totalItems") val totalItems: Int = 0,
    @SerializedName("itemsPerPage") val itemsPerPage: Int = 10
)

// Food Log class
data class FoodLog(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("calories") val calories: Int = 0,
    @SerializedName("time") val time: String = "",
    @SerializedName("category") val category: String = ""
)
