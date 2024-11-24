package com.amikom.sweetlife.di

import android.app.Application
import com.amikom.sweetlife.data.manager.LocalUserManagerImpl
import com.amikom.sweetlife.domain.manager.LocalUserManager
import com.amikom.sweetlife.domain.usecases.AppEntryUseCases
import com.amikom.sweetlife.domain.usecases.ReadAppEntry
import com.amikom.sweetlife.domain.usecases.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ) : LocalUserManager = LocalUserManagerImpl(application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    ) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )

}