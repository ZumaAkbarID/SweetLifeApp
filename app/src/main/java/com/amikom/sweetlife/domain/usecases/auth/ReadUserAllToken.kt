package com.amikom.sweetlife.domain.usecases.auth

import com.amikom.sweetlife.domain.manager.LocalAuthUserManager
import kotlinx.coroutines.flow.Flow

class ReadUserAllToken(
    private val localAuthUserManager: LocalAuthUserManager
) {
    operator fun invoke(): Flow<List<Pair<String, String?>>> {
        return localAuthUserManager.getAllToken()
    }
}