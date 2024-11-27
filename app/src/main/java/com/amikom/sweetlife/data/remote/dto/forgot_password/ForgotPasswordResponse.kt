package com.amikom.sweetlife.data.remote.dto.forgot_password

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class Data(

	@field:SerializedName("expire")
	val expire: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
