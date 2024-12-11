package com.amikom.sweetlife.data.remote.dto.dashboard

import com.google.gson.annotations.SerializedName

data class DashboardResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class Sugar(

	@field:SerializedName("current")
	val current: Double? = null,

	@field:SerializedName("satisfication")
	val satisfication: String? = null,

	@field:SerializedName("percent")
	val percent: Double? = null,

	@field:SerializedName("target")
	val target: Double? = null
)

data class User(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("diabetes")
	val diabetes: Boolean? = null
)

data class DailyProgress(

	@field:SerializedName("carbs")
	val carbs: Carbs? = null,

	@field:SerializedName("calories")
	val calories: Calories? = null,

	@field:SerializedName("sugar")
	val sugar: Sugar? = null
)

data class Data(

	@field:SerializedName("dailyProgress")
	val dailyProgress: DailyProgress? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("status")
	val status: Status? = null
)

data class Carbs(

	@field:SerializedName("current")
	val current: Double? = null,

	@field:SerializedName("satisfication")
	val satisfication: String? = null,

	@field:SerializedName("percent")
	val percent: Double? = null,

	@field:SerializedName("target")
	val target: Double? = null
)

data class Calories(

	@field:SerializedName("current")
	val current: Double? = null,

	@field:SerializedName("satisfication")
	val satisfication: String? = null,

	@field:SerializedName("percent")
	val percent: Double? = null,

	@field:SerializedName("target")
	val target: Double? = null
)

data class Status(

	@field:SerializedName("satisfication")
	val satisfication: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
