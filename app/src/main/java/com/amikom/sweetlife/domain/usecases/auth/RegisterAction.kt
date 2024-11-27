package com.amikom.sweetlife.domain.usecases.auth

import androidx.lifecycle.LiveData
import com.amikom.sweetlife.data.model.UserModel
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class RegisterAction(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): LiveData<Result<Boolean>> {
        return authRepository.register(email = email, password = password)
    }
}