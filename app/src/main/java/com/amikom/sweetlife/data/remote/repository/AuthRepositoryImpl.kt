package com.amikom.sweetlife.data.remote.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.amikom.sweetlife.data.model.ForgotPasswordModel
import com.amikom.sweetlife.data.model.NewTokenModel
import com.amikom.sweetlife.data.model.UserModel
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.data.remote.dto.ErrorResponse
import com.amikom.sweetlife.data.remote.json_request.ForgotPasswordRequest
import com.amikom.sweetlife.data.remote.json_request.LoginRequest
import com.amikom.sweetlife.data.remote.json_request.RefreshTokenRequest
import com.amikom.sweetlife.data.remote.json_request.RegisterRequest
import com.amikom.sweetlife.data.remote.retrofit.AuthApiService
import com.amikom.sweetlife.domain.manager.LocalAuthUserManager
import com.amikom.sweetlife.domain.repository.AuthRepository
import com.amikom.sweetlife.util.AppExecutors
import com.google.gson.Gson

class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val appExecutors: AppExecutors,
    private val localAuthUserManager: LocalAuthUserManager
) : AuthRepository {
    override suspend fun login(email: String, password: String): LiveData<Result<UserModel>> {
        val result = MediatorLiveData<Result<UserModel>>()
        result.value = Result.Loading

        try {
            // Create request
            val loginRequest = LoginRequest(email = email, password = password)

            // Perform API call
            val response = authApiService.login(loginRequest)

            if (response.isSuccessful) {
                // Parse response body
                val loginUser = response.body()?.data
                val token = loginUser?.token ?: throw Exception("Data Token is null")
                val user = UserModel(
                    email = loginUser.user?.email ?: "",
                    name = loginUser.user?.name ?: "",
                    gender = loginUser.user?.gender ?: "",
                    token = token.accessToken ?: throw Exception("Token is null"),
                    refreshToken = token.refreshToken ?: throw Exception("Refresh Token is null"),
                    isLogin = true,
                    hasHealthProfile = loginUser.user?.hasHealthProfile ?: false
                )
                // Update result on main thread
                appExecutors.mainThread.execute {
                    result.value = Result.Success(user)
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

    override suspend fun register(
        email: String,
        password: String
    ): LiveData<Result<Boolean>> {
        val result = MediatorLiveData<Result<Boolean>>()
        result.value = Result.Loading

        try {
            // Create request
            val registerRequest = RegisterRequest(email = email, password = password)

            // Perform API call
            val response = authApiService.register(registerRequest)

            if (response.isSuccessful) {
                // Parse response body
                val registerStatus = response.body()?.status ?: false
                val messageBody = response.body()?.message ?: "Server Error"

                if(registerStatus && messageBody == "action success") {
                    // Update result on main thread
                    appExecutors.mainThread.execute {
                        result.value = Result.Success(registerStatus)
                    }
                } else {
                    throw Exception(messageBody)
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

    override suspend fun changePassword(oldPassword: String, newPassword: String) {
        TODO("Not yet implemented")
    }

    override suspend fun forgotPassword(email: String) : LiveData<Result<ForgotPasswordModel>> {
        val result = MediatorLiveData<Result<ForgotPasswordModel>>()
        result.value = Result.Loading

        try {
            // Create request
            val forgotPasswordRequest = ForgotPasswordRequest(email = email)

            // Perform API call
            val response = authApiService.forgotPassword(forgotPasswordRequest)

            if (response.isSuccessful) {
                // Parse response body
                val registerStatus = response.body()?.status ?: false
                val messageBody = response.body()?.message ?: "Server Error"

                if(registerStatus && messageBody == "action success") {
                    val forgotPasswordModelResult = ForgotPasswordModel(
                        email = response.body()?.data?.email ?: "",
                        expire = response.body()?.data?.expire ?: "",
                    )

                    // Update result on main thread
                    appExecutors.mainThread.execute {
                        result.value = Result.Success(forgotPasswordModelResult)
                    }
                } else {
                    throw Exception(messageBody)
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

    override suspend fun logout(): LiveData<Result<Boolean>> {
        val result = MediatorLiveData<Result<Boolean>>()
        result.value = Result.Loading

        try {
            // Perform API call
            val response = authApiService.logout()

            if (response.isSuccessful) {
                // Parse response body
                val registerStatus = response.body()?.status ?: false
                val messageBody = response.body()?.message ?: "Server Error"

                if(registerStatus && messageBody == "action success") {
                    localAuthUserManager.logout()

                    // Update result on main thread
                    appExecutors.mainThread.execute {
                        result.value = Result.Success(data = true)
                    }
                } else {
                    throw Exception(messageBody)
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

    override suspend fun refreshToken(refreshToken: String): Result<NewTokenModel> {
        return try {
            val refreshTokenRequest = RefreshTokenRequest(refresh_token = refreshToken)

            val response = authApiService.refreshToken(refreshTokenRequest)

            if (response.isSuccessful) {
                val mainResponse = response.body()
                val dataResponse = response.body()?.data

                if (mainResponse?.status == true && mainResponse.message == "action success") {
                    Result.Success(
                        NewTokenModel(
                            accessToken = dataResponse?.accessToken.orEmpty(),
                            refreshToken = dataResponse?.refreshToken.orEmpty(),
                            type = dataResponse?.type.orEmpty()
                        )
                    )
                } else {
                    Result.Error(mainResponse?.message ?: "Unknown error")
                }
            } else {
                val errorBody = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                Result.Error(errorBody?.error ?: response.message())
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unexpected error occurred")
        }
    }
}