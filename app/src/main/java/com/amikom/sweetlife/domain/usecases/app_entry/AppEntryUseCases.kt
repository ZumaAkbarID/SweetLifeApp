package com.amikom.sweetlife.domain.usecases.app_entry

data class AppEntryUseCases(
    val readAppEntry: ReadAppEntry,
    val saveAppEntry: SaveAppEntry,
    val updateAppThemeMode: UpdateAppThemeMode,
    val getAppThemeMode: GetAppThemeMode
)
