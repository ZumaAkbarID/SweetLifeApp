package com.amikom.sweetlife.data.model

data class DashboardModel(
    val message: String,
    val status: Boolean,
    val data: Data
)

data class Data(
    val dailyProgress: DailyProgress,
    val status: Status,
    val user: User
)

data class DailyProgress(
    val calorie: ProgressDetail,
    val glucose: ProgressDetail
)

data class ProgressDetail(
    val current: Int,
    val percentage: Double,
    val satisfaction: String,
    val target: Int
)

data class Status(
    val message: String,
    val satisfaction: String
)

data class User(
    val diabetesType: String,
    val name: String
)
