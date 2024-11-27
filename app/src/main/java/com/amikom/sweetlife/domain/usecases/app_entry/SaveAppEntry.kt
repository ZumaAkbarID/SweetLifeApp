package com.amikom.sweetlife.domain.usecases.app_entry

import com.amikom.sweetlife.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke() {
        localUserManager.saveAppEntry()
    }
}