package com.amikom.sweetlife.ui.screen.History
import com.google.gson.annotations.SerializedName

// Root model
data class ApiResponse(
	@SerializedName("status") val status: String,
	@SerializedName("data") val data: Data
)

// Data class
data class Data(
	@SerializedName("food_history") val foodHistory: List<FoodHistory>
)

// FoodHistory class
data class FoodHistory(
	@SerializedName("date") val date: String,
	@SerializedName("total_calories") val totalCalories: Int,  // Adjusted field name
	@SerializedName("entries") val entries: List<Entry>
)

// Entry class
data class Entry(
	@SerializedName("id") val id: String,
	@SerializedName("food_name") val foodName: String,  // Adjusted field name
	@SerializedName("calories") val calories: Int,
	@SerializedName("time") val time: String,
	@SerializedName("total_units") val totalUnits: Int  // Added missing field
)
