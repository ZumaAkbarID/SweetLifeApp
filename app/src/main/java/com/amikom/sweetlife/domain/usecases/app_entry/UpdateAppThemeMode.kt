package com.amikom.sweetlife.domain.usecases.app_entry

import com.amikom.sweetlife.domain.manager.LocalUserManager

class UpdateAppThemeMode(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke(isDarkMode: Boolean) {
        localUserManager.updateAppThemeMode(isDarkMode = isDarkMode)
    }
}