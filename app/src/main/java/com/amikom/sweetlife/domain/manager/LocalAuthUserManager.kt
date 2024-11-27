package com.amikom.sweetlife.domain.manager

import com.amikom.sweetlife.data.model.UserModel
import kotlinx.coroutines.flow.Flow


interface LocalAuthUserManager {

    suspend fun saveInfoLogin(userModel: UserModel)

    fun readInfoLogin(): Flow<Boolean>

    fun getAllToken(): Flow<List<Pair<String, String?>>>
}