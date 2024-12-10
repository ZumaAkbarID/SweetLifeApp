package com.amikom.sweetlife.domain.manager

import kotlinx.coroutines.flow.Flow


interface LocalUserManager {

    suspend fun saveAppEntry()

    suspend fun updateAppThemeMode(isDarkMode: Boolean)

    fun readAppEntry(): Flow<Boolean>

    fun getAppThemeMode(): Flow<Boolean>
}