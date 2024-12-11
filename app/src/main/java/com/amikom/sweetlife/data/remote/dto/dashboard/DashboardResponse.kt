package com.amikom.sweetlife.data.remote.dto.dashboard

import com.google.gson.annotations.SerializedName

data class DashboardResponse(
	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class User(
	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("diabetesType")
	val diabetesType: Boolean? = null,

	@field:SerializedName("diabetes")
	val diabetes: String? = null
)

data class ProgressDetail(
	@field:SerializedName("current")
	val current: Double? = null,

	@field:SerializedName("percent")
	val percentage: Double? = null,

	@field:SerializedName("satisfication")
	val satisfaction: String? = null,

	@field:SerializedName("target")
	val target: Double? = null
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
	@field:SerializedName("sugar")
	val glucose: ProgressDetail? = null,

	@field:SerializedName("calories")
	val calorie: ProgressDetail? = null,

	@field:SerializedName("carbs")
	val carbs: ProgressDetail? = null
)

data class Status(
	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("satisfication")
	val satisfaction: String? = null
)