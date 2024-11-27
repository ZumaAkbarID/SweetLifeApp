package com.amikom.sweetlife.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.amikom.sweetlife.data.model.UserModel
import com.amikom.sweetlife.data.remote.dto.LoginResponse
import com.amikom.sweetlife.data.remote.json_request.LoginRequest
import com.amikom.sweetlife.data.remote.retrofit.AuthApiService
import com.amikom.sweetlife.domain.repository.AuthRepository
import com.amikom.sweetlife.util.AppExecutors
import retrofit2.Response
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.data.remote.dto.ErrorResponse
import com.google.gson.Gson
import kotlin.math.log

class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val appExecutors: AppExecutors
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
                    isLogin = true
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

    override suspend fun register(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun changePassword(oldPassword: String, newPassword: String) {
        TODO("Not yet implemented")
    }

    override suspend fun forgotPassword(email: String) {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }

    override suspend fun refreshToken(refreshToken: String) {
        TODO("Not yet implemented")
    }

}