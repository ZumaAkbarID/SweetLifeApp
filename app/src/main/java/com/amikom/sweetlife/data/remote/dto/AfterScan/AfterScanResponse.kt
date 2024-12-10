package com.amikom.sweetlife.data.remote.dto.AfterScan

import com.google.gson.annotations.SerializedName

data class AfterScanResponse(

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
	val protein: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("fat")
	val fat: Int? = null,

	@field:SerializedName("calories")
	val calories: Int? = null,

	@field:SerializedName("sugar")
	val sugar: Any? = null,

	@field:SerializedName("carbohydrate")
	val carbohydrate: Int? = null
)
