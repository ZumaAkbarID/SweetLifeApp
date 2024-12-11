package com.amikom.sweetlife.data.remote.dto.scan

import com.google.gson.annotations.SerializedName

data class SaveFoodResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
