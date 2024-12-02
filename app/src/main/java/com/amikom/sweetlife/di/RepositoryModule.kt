package com.amikom.sweetlife.di

import com.amikom.sweetlife.data.remote.repository.RekomenRepositoryImpl
import com.amikom.sweetlife.data.remote.retrofit.FeatureApiService
import com.amikom.sweetlife.domain.repository.RekomenRepository
import com.amikom.sweetlife.util.AppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRekomenRepository(
        featureApiService: FeatureApiService,
        appExecutors: AppExecutors
    ): RekomenRepository {
        return RekomenRepositoryImpl(featureApiService)
    }
}