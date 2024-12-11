package com.amikom.sweetlife.data.model

data class DashboardModel(
    val status: Boolean,
    val data: Data
)

data class Data(
    val dailyProgress: DailyProgress,
    val status: Status,
    val user: User
)

data class DailyProgress(
    val calories: ProgressDetail,
    val carbs: ProgressDetail,
    val sugar: ProgressDetail
)

data class ProgressDetail(
    val current: Double,
    val percent: Double,
    val satisfaction: String,
    val target: Double
)

data class Status(
    val message: String,
    val satisfaction: String
)

data class User(
    val name: String,
    val diabetes: Boolean,
    val diabetesType : String
)
