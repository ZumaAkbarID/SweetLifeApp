package com.amikom.sweetlife.domain.usecases.auth

import com.amikom.sweetlife.data.model.NewTokenModel
import com.amikom.sweetlife.domain.manager.LocalAuthUserManager

class SaveHealthProfile(
    private val localAuthUserManager: LocalAuthUserManager
) {
    suspend operator fun invoke(hasHealth: Boolean) {
        return localAuthUserManager.saveNewHasHealth(hasHealth)
    }
}