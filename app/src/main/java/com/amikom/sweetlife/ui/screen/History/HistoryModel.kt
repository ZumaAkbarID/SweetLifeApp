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
		@SerializedName("totalCalories") val totalCalories: Int,
		@SerializedName("entries") val entries: List<Entry>
	)

	// Entry class
	data class Entry(
		@SerializedName("id") val id: String,
		@SerializedName("foodName") val foodName: String,
		@SerializedName("calories") val calories: Int,
		@SerializedName("time") val time: String
	)