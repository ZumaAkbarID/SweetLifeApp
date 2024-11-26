package com.amikom.sweetlife.domain.usecases.auth

import androidx.lifecycle.LiveData
import com.amikom.sweetlife.data.model.UserModel
import com.amikom.sweetlife.data.remote.dto.LoginResponse
import com.amikom.sweetlife.domain.repository.AuthRepository
import retrofit2.Response
import com.amikom.sweetlife.data.remote.Result

class LoginAction(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(email: String, password: String): LiveData<Result<UserModel>> {
        return authRepository.login(email = email, password = password)
    }

}