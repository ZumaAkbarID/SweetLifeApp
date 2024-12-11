package com.amikom.sweetlife.data.remote.dto.scan

import com.google.gson.annotations.SerializedName

data class ScanResponse(

	@field:SerializedName("food_list")
	val foodList: List<FoodListItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class FoodListItem(

	@field:SerializedName("unit")
	val unit: Int? = null,

	@field:SerializedName("protein")
	val protein: Double? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("fat")
	val fat: Double? = null,

	@field:SerializedName("calories")
	val calories: Double? = null,

	@field:SerializedName("sugar")
	val sugar: Double? = null,

	@field:SerializedName("carbohydrate")
	val carbohydrate: Double? = null
)
