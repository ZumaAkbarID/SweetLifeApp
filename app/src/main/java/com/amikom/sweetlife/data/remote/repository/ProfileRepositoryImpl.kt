package com.amikom.sweetlife.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.amikom.sweetlife.data.model.DailyProgress
import com.amikom.sweetlife.data.model.DashboardModel
import com.amikom.sweetlife.data.model.Data
import com.amikom.sweetlife.data.model.DiabetesPrediction
import com.amikom.sweetlife.data.model.HealthProfileModel
import com.amikom.sweetlife.data.model.ProfileModel
import com.amikom.sweetlife.data.model.ProgressDetail
import com.amikom.sweetlife.data.model.Status
import com.amikom.sweetlife.data.model.User
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.data.remote.dto.ErrorResponse
import com.amikom.sweetlife.data.remote.retrofit.FeatureApiService
import com.amikom.sweetlife.domain.repository.DashboardRepository
import com.amikom.sweetlife.domain.repository.ProfileRepository
import com.amikom.sweetlife.util.AppExecutors
import com.amikom.sweetlife.util.Constants
import com.google.gson.Gson

class ProfileRepositoryImpl(
    private val featureApiService: FeatureApiService,
    private val appExecutors: AppExecutors
) : ProfileRepository {
    private val defaultErrorValue = Constants.DEFAULT_ERROR_TEXT

    override suspend fun fetchDataProfile(): LiveData<Result<ProfileModel>> {
        val result = MediatorLiveData<Result<ProfileModel>>()
        result.value = Result.Loading

        try {
            // Perform API call
            val response = featureApiService.getProfile()

            if (response.isSuccessful) {
                // Parse response body
                val rawData = response.body()
                val data = response.body()?.data

                val profileModel = ProfileModel(
                    id = data?.id ?: "",
                    email = data?.email ?: defaultErrorValue,
                    name = data?.name ?: defaultErrorValue,
                    gender = data?.gender ?: defaultErrorValue,
                    dateOfBirth = data?.dateOfBirth ?: defaultErrorValue
                )

                // Update result on main thread
                appExecutors.mainThread.execute {
                    result.value = Result.Success(profileModel)
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

    override suspend fun fetchHealthProfile(): LiveData<Result<HealthProfileModel>> {
        val result = MediatorLiveData<Result<HealthProfileModel>>()
        result.value = Result.Loading

        try {
            // Perform API call
            val response = featureApiService.getHealth()

            if (response.isSuccessful) {
                // Parse response body
                val rawData = response.body()
                val data = response.body()?.data

                val healthProfileModel = HealthProfileModel(
                    userId = data?.userId ?: "",
                    height = data?.height ?: 0.0,
                    weight = data?.weight ?: 0.0,
                    isDiabetic = data?.isDiabetic ?: false,
                    smokingHistory = data?.smokingHistory ?: defaultErrorValue,
                    hasHeartDisease = data?.hasHeartDisease ?: false,
                    activityLevel = data?.activityLevel ?: defaultErrorValue,
                    diabetesPrediction = DiabetesPrediction(
                        riskPercentage = data?.diabetesPrediction?.riskPercentage ?: 0,
                        riskLevel = data?.diabetesPrediction?.riskLevel ?: defaultErrorValue,
                        note = data?.diabetesPrediction?.note ?: defaultErrorValue
                    )
                )

                // Update result on main thread
                appExecutors.mainThread.execute {
                    result.value = Result.Success(healthProfileModel)
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