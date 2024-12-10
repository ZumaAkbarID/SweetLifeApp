package com.amikom.sweetlife.domain.usecases.auth

import com.amikom.sweetlife.data.model.NewTokenModel
import com.amikom.sweetlife.domain.manager.LocalAuthUserManager

class SaveNewToken(
    private val localAuthUserManager: LocalAuthUserManager
) {
    suspend operator fun invoke(newToken: NewTokenModel) {
        return localAuthUserManager.saveNewTokenInfo(newToken = newToken)
    }
}