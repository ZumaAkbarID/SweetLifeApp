package com.amikom.sweetlife.data.remote.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.amikom.sweetlife.data.model.DailyProgress
import com.amikom.sweetlife.data.model.DashboardModel
import com.amikom.sweetlife.data.model.Data
import com.amikom.sweetlife.data.model.FoodRequest
import com.amikom.sweetlife.data.model.ProgressDetail
import com.amikom.sweetlife.data.model.Status
import com.amikom.sweetlife.data.model.User
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.data.remote.dto.ErrorResponse
import com.amikom.sweetlife.data.remote.dto.scan.FindFoodResponse
import com.amikom.sweetlife.data.remote.dto.scan.SaveFoodResponse
import com.amikom.sweetlife.data.remote.dto.scan.ScanResponse
import com.amikom.sweetlife.data.remote.json_request.FindFoodRequest
import com.amikom.sweetlife.data.remote.retrofit.FeatureApiService
import com.amikom.sweetlife.domain.repository.DashboardRepository
import com.amikom.sweetlife.util.AppExecutors
import com.google.gson.Gson
import okhttp3.MultipartBody
import java.io.File

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
                            diabetes = data?.user?.diabetes ?: false,
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

    override suspend fun fetchFindFood(
        name: String,
        weight: Int
    ): LiveData<Result<FindFoodResponse>> {
        val result = MediatorLiveData<Result<FindFoodResponse>>()
        result.value = Result.Loading

        try {
            // Perform API call
            val response = featureApiService.findFood(FindFoodRequest(
                name,
                weight
            ))

            if (response.isSuccessful) {
                // Parse response body
                val rawData = response.body()
                val data = response.body()?.data

                val findFoodResponse = FindFoodResponse(
                    data = com.amikom.sweetlife.data.remote.dto.scan.Data(
                        carbohydrates = data?.carbohydrates ?: 0.0,
                        fat = data?.fat ?: 0.0,
                        weight = data?.weight ?: 0.0,
                        calories = data?.calories ?: 0.0,
                        protein = data?.protein ?: 0.0,
                        sugar = data?.sugar ?: 0.0,
                        name = data?.name ?: ""
                    ),
                    message = rawData?.message ?: "",
                    status = rawData?.status ?: false
                )

                // Update result on main thread
                appExecutors.mainThread.execute {
                    result.value = Result.Success(findFoodResponse)
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

    override suspend fun saveFoodRequest(listFood: FoodRequest): LiveData<Result<SaveFoodResponse>> {
        val result = MediatorLiveData<Result<SaveFoodResponse>>()
        result.value = Result.Loading

        try {
            // Perform API call
            val response = featureApiService.saveFood(listFood)

            if (response.isSuccessful) {
                // Parse response body
                val rawData = response.body()

                val saveFoodResponse = SaveFoodResponse(
                    status = rawData?.status ?: false,
                    message = rawData?.message ?: ""
                )

                // Update result on main thread
                appExecutors.mainThread.execute {
                    result.value = Result.Success(saveFoodResponse)
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

    override suspend fun scanFood(image:  MultipartBody.Part): LiveData<Result<ScanResponse>> {
        val result = MediatorLiveData<Result<ScanResponse>>()
        result.value = Result.Loading

        try {
            // Perform API call
            val response = featureApiService.foodScan(image)
            Log.d("UPLOAD_SCAN", "Raw REQUEST: $image")
            Log.d("UPLOAD_SCAN", "Raw response: ${response.body()}")

            if (response.isSuccessful) {
                // Update result on main thread
                appExecutors.mainThread.execute {
                    result.value = Result.Success(response.body()!!)
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