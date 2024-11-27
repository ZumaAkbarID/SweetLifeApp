package com.amikom.sweetlife.ui.screen.Dashboard

import com.google.gson.annotations.SerializedName

data class DashboardModel(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Calorie(

	@field:SerializedName("current")
	val current: Int? = null,

	@field:SerializedName("percentage")
	val percentage: Int? = null,

	@field:SerializedName("satisfaction")
	val satisfaction: String? = null,

	@field:SerializedName("target")
	val target: Int? = null
)

data class DailyProgress(

	@field:SerializedName("glucose")
	val glucose: Glucose? = null,

	@field:SerializedName("calorie")
	val calorie: Calorie? = null
)

data class Data(

	@field:SerializedName("dailyProgress")
	val dailyProgress: DailyProgress? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("status")
	val status: Status? = null
)

data class Status(

	@field:SerializedName("satisfaction")
	val satisfaction: String? = null,

	@field:SerializedName("message")
	val message: String? = null
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
	val percentage: Int? = null,

	@field:SerializedName("satisfaction")
	val satisfaction: String? = null,

	@field:SerializedName("target")
	val target: Int? = null
)
