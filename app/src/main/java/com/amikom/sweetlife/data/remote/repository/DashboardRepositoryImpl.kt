package com.amikom.sweetlife.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.amikom.sweetlife.data.model.DailyProgress
import com.amikom.sweetlife.data.model.DashboardModel
import com.amikom.sweetlife.data.model.Data
import com.amikom.sweetlife.data.model.ProgressDetail
import com.amikom.sweetlife.data.model.Status
import com.amikom.sweetlife.data.model.User
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.data.remote.dto.ErrorResponse
import com.amikom.sweetlife.data.remote.retrofit.FeatureApiService
import com.amikom.sweetlife.domain.repository.DashboardRepository
import com.amikom.sweetlife.util.AppExecutors
import com.google.gson.Gson

class DashboardRepositoryImpl(
    private val featureApiService: FeatureApiService,
    private val appExecutors: AppExecutors
) : DashboardRepository {
    override suspend fun fetchDataDashboard(): LiveData<Result<DashboardModel>> {
        val result = MediatorLiveData<Result<DashboardModel>>()
        result.value = Result.Loading

        try {
            // Perform API call
            val response = featureApiService.dashboard()

            if (response.isSuccessful) {
                // Parse response body
                val rawData = response.body()
                val data = response.body()?.data

                val dashboardModel = DashboardModel(
                    message = rawData?.message ?: "",
                    status = rawData?.status ?: false,
                    data = Data(
                        dailyProgress = DailyProgress(
                            calorie = ProgressDetail(
                                current = data?.dailyProgress?.calorie?.current ?: 0,
                                percentage = data?.dailyProgress?.calorie?.percentage ?: 0.0,
                                satisfaction = data?.dailyProgress?.calorie?.satisfaction ?: "",
                                target = data?.dailyProgress?.calorie?.target ?: 0
                            ),
                            glucose = ProgressDetail(
                                current = data?.dailyProgress?.glucose?.current ?: 0,
                                percentage = data?.dailyProgress?.glucose?.percentage ?: 0.0,
                                satisfaction = data?.dailyProgress?.glucose?.satisfaction ?: "",
                                target = data?.dailyProgress?.glucose?.target ?: 0
                            )
                        ),
                        status = Status(
                            message = data?.status?.message ?: "",
                            satisfaction = data?.status?.satisfaction ?: ""
                        ),
                        user = User(
                            name = data?.user?.name ?: "",
                            diabetesType = data?.user?.diabetesType ?: ""
                        )
                    )
                )


                // Update result on main thread
                appExecutors.mainThread.execute {
                    result.value = Result.Success(dashboardModel)
                }
            } else {
                // Handle error response
                val errorBody = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                val message = errorBody?.error ?: response.message()
                appExecutors.mainThread.execute {
                    result.value = Result.Error(message)
                }
            }
        } catch (e: Exception) {
            // Handle exceptions
            appExecutors.mainThread.execute {
                result.value = e.message?.let { Result.Error(it) }
            }
        }

        return result
    }
}