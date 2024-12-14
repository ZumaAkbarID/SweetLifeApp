package com.amikom.sweetlife.domain.usecases.auth

import androidx.lifecycle.LiveData
import com.amikom.sweetlife.data.model.UserModel
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class LogoutAction(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(): LiveData<Result<Boolean>> {
        return authRepository.logout()
    }

}