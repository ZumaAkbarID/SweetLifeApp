package com.amikom.sweetlife.domain.repository

import androidx.lifecycle.LiveData
import com.amikom.sweetlife.data.model.UserModel
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.data.remote.dto.LoginResponse
import retrofit2.Response

interface AuthRepository {

    suspend fun login(email: String, password: String): LiveData<Result<UserModel>>

    suspend fun register(email: String, password: String)

    suspend fun changePassword(oldPassword: String, newPassword: String)

    suspend fun forgotPassword(email: String)

    suspend fun logout()

    suspend fun refreshToken(refreshToken: String)

}