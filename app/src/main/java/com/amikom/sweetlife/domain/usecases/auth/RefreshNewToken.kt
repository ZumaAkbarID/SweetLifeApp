package com.amikom.sweetlife.domain.usecases.auth

import androidx.lifecycle.LiveData
import com.amikom.sweetlife.data.model.NewTokenModel
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.domain.repository.AuthRepository

class RefreshNewTokenAction(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(refreshToken: String) : Result<NewTokenModel> {
        return authRepository.refreshToken(refreshToken = refreshToken)
    }
}