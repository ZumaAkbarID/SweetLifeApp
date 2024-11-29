package com.amikom.sweetlife.data.remote.dto.dashboard

import com.google.gson.annotations.SerializedName

data class DashboardResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class User(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("diabetesType")
	val diabetesType: String? = null
)

data class Glucose(

	@field:SerializedName("current")
	val current: Int? = null,

	@field:SerializedName("percentage")
	val percentage: Double? = null,

	@field:SerializedName("satisfaction")
	val satisfaction: String? = null,

	@field:SerializedName("target")
	val target: Int? = null
)

data class Data(

	@field:SerializedName("dailyProgress")
	val dailyProgress: DailyProgress? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("status")
	val status: Status? = null
)

data class DailyProgress(

	@field:SerializedName("glucose")
	val glucose: Glucose? = null,

	@field:SerializedName("calorie")
	val calorie: Calorie? = null
)

data class Status(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("satisfaction")
	val satisfaction: String? = null
)

data class Calorie(

	@field:SerializedName("current")
	val current: Int? = null,

	@field:SerializedName("percentage")
	val percentage: Double? = null,

	@field:SerializedName("satisfaction")
	val satisfaction: String? = null,

	@field:SerializedName("target")
	val target: Int? = null
)
