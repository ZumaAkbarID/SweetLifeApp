package com.amikom.sweetlife.domain.usecases.auth

import com.amikom.sweetlife.domain.manager.LocalAuthUserManager
import kotlinx.coroutines.flow.Flow

class CheckHasHealthProfile(
    private val localAuthUserManager: LocalAuthUserManager
) {
    operator fun invoke(): Flow<Boolean> {
        return localAuthUserManager.readHasHealth()
    }
}