package com.amikom.sweetlife.domain.usecases.auth

import com.amikom.sweetlife.data.model.UserModel
import com.amikom.sweetlife.domain.manager.LocalAuthUserManager

class SaveUserInfoLogin(
    private val localAuthUserManager: LocalAuthUserManager
) {
    suspend operator fun invoke(userModel: UserModel) {
        localAuthUserManager.saveInfoLogin(userModel = userModel)
    }
}