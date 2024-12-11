package com.amikom.sweetlife.data.remote.dto.scan

data class ScanResponse(
	val foodList: List<FoodListItem?>? = null,
	val message: String? = null,
	val status: Boolean? = null
)

data class FoodListItem(
	val unit: Int? = null,
	val protein: Any? = null,
	val name: String? = null,
	val fat: Any? = null,
	val calories: Int? = null,
	val sugar: Any? = null,
	val carbohydrate: Any? = null
)

