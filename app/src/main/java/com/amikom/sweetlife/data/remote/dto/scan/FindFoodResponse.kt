package com.amikom.sweetlife.data.remote.dto.scan

import com.google.gson.annotations.SerializedName

data class FindFoodResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class Data(

	@field:SerializedName("Carbohydrates")
	val carbohydrates: Double? = null,

	@field:SerializedName("Fat")
	val fat: Double? = null,

	@field:SerializedName("Weight")
	val weight: Double? = null,

	@field:SerializedName("Calories")
	val calories: Double? = null,

	@field:SerializedName("Protein")
	val protein: Double? = null,

	@field:SerializedName("Sugar")
	val sugar: Double? = null,

	@field:SerializedName("Name")
	val name: String? = null
)
