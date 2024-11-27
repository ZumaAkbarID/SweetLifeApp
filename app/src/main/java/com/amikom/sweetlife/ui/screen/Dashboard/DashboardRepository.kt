package com.amikom.sweetlife.ui.screen.Dashboard

import android.util.Log
import kotlinx.coroutines.delay
import javax.inject.Inject

class DashboardRepository @Inject constructor() {
    //dummy cik
    suspend fun fetchDashboardData(): DashboardModel {
        Log.d("DashboardRepository", "Fetching")
        delay(2000)
        val dummyData = DashboardModel(
            status = "success",
            data = Data(
                dailyProgress = DailyProgress(
                    glucose = Glucose(
                        current = 120,
                        percentage = 60,
                        satisfaction = "Good",
                        target = 200
                    ),
                    calorie = Calorie(
                        current = 2000,
                        percentage = 75,
                        satisfaction = "Satisfactory",
                        target = 2000
                    )
                ),
                user = User(
                    name = "John Doe",
                    diabetesType = "Type 1"
                ),
                status = Status(
                    satisfaction = "Improving",
                    message = "Keep up the good work"
                )
            )
        )
        Log.d("DashboardRepository", "Dummy data ready: $dummyData")
        return dummyData
    }
}
