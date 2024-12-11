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
                    status = rawData?.status ?: false,
                    data = Data(
                        dailyProgress = DailyProgress(
                            calories = ProgressDetail(
                                current = data?.dailyProgress?.calories?.current ?: 0.0,
                                percent = data?.dailyProgress?.calories?.percent ?: 0.0,
                                satisfaction = data?.dailyProgress?.calories?.satisfication ?: "",
                                target = data?.dailyProgress?.calories?.target ?: 0.0
                            ),
                            carbs = ProgressDetail(
                                current = data?.dailyProgress?.carbs?.current ?: 0.0,
                                percent = data?.dailyProgress?.carbs?.percent ?: 0.0,
                                satisfaction = data?.dailyProgress?.carbs?.satisfication ?: "",
                                target = data?.dailyProgress?.carbs?.target ?: 0.0
                            ),
                            sugar = ProgressDetail(
                                current = data?.dailyProgress?.sugar?.current ?: 0.0,
                                percent = data?.dailyProgress?.sugar?.percent ?: 0.0,
                                satisfaction = data?.dailyProgress?.sugar?.satisfication ?: "",
                                target = data?.dailyProgress?.sugar?.target ?: 0.0
                            ),
                        ),
                        status = Status(
                            message = data?.status?.message ?: "",
                            satisfaction = data?.status?.satisfication ?: ""
                        ),
                        user = User(
                            name = data?.user?.name ?: "",
                            diabetes = data?.user?.diabetes ?: false
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