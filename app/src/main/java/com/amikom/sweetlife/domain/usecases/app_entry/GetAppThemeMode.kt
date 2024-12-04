package com.amikom.sweetlife.domain.usecases.app_entry

import com.amikom.sweetlife.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class GetAppThemeMode(
    private val localUserManager: LocalUserManager
) {
    operator fun invoke(): Flow<Boolean> {
        return localUserManager.getAppThemeMode()
    }
}