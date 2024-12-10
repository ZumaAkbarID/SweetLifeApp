package com.amikom.sweetlife.data.remote.dto.health_profile

import com.google.gson.annotations.SerializedName

data class CreateHealthResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
