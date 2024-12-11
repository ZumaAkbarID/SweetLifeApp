package com.amikom.sweetlife.data.model

data class DashboardModel(
    val status: Boolean,
    val data: Data
)

data class Data(
    val dailyProgress: DailyProgress,
    val status: Status,
    val user: User,
)

data class DailyProgress(
    val calorie: ProgressDetail,
    val glucose: ProgressDetail,
    val carbs: ProgressDetail,
)

data class ProgressDetail(
    val current: Double = 0.0,
    val percentage: Double = 0.0,
    val satisfaction: String = "",
    val target: Double = 0.0
)

data class Status(
    val message: String,
    val satisfaction: String
)

data class User(
    val diabetesType: Boolean,
    val name: String,
    val diabetes: String
)
