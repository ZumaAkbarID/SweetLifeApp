package com.amikom.sweetlife.ui.screen.History

import com.google.gson.annotations.SerializedName

data class HistoryModel(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Pagination(

	@field:SerializedName("totalItems")
	val totalItems: Int? = null,

	@field:SerializedName("itemsPerPage")
	val itemsPerPage: Int? = null,

	@field:SerializedName("totalPages")
	val totalPages: Int? = null,

	@field:SerializedName("currentPage")
	val currentPage: Int? = null
)

data class Icon(

	@field:SerializedName("backgroundColor")
	val backgroundColor: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class EntriesItem(

	@field:SerializedName("foodName")
	val foodName: String? = null,

	@field:SerializedName("editable")
	val editable: Boolean? = null,

	@field:SerializedName("icon")
	val icon: Icon? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("calories")
	val calories: Int? = null,

	@field:SerializedName("time")
	val time: String? = null
)

data class FoodLogsItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("entries")
	val entries: List<EntriesItem?>? = null,

	@field:SerializedName("totalCalories")
	val totalCalories: Int? = null
)

data class Data(

	@field:SerializedName	("pagination")
	val pagination: Pagination? = null,

	@field:SerializedName("foodLogs")
	val foodLogs: List<FoodLogsItem?>? = null
)
