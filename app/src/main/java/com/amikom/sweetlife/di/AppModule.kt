package com.amikom.sweetlife.di

import android.app.Application
import com.amikom.sweetlife.BuildConfig
import com.amikom.sweetlife.data.manager.LocalAuthUserManagerImpl
import com.amikom.sweetlife.data.manager.LocalUserManagerImpl
import com.amikom.sweetlife.data.remote.repository.AuthRepositoryImpl
import com.amikom.sweetlife.data.remote.retrofit.AuthApiService
import com.amikom.sweetlife.domain.manager.LocalAuthUserManager
import com.amikom.sweetlife.domain.manager.LocalUserManager
import com.amikom.sweetlife.domain.repository.AuthRepository
import com.amikom.sweetlife.domain.usecases.app_entry.AppEntryUseCases
import com.amikom.sweetlife.domain.usecases.app_entry.ReadAppEntry
import com.amikom.sweetlife.domain.usecases.app_entry.SaveAppEntry
import com.amikom.sweetlife.domain.usecases.auth.LoginAction
import com.amikom.sweetlife.domain.usecases.auth.AuthUseCases
import com.amikom.sweetlife.domain.usecases.auth.CheckIsUserLogin
import com.amikom.sweetlife.domain.usecases.auth.ReadUserAllToken
import com.amikom.sweetlife.domain.usecases.auth.SaveUserInfoLogin
import com.amikom.sweetlife.util.AppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val baseUrl: String = if(BuildConfig.DEBUG) BuildConfig.BASE_URL_DEV else BuildConfig.BASE_URL_PROD

    private val loggingInterceptor = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    } else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

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

    @Provides
    @Singleton
    fun provideLocalAuthUserManager(
        application: Application
    ): LocalAuthUserManager {
        return LocalAuthUserManagerImpl(application)
    }

    @Provides
    @Singleton
    fun provideAuthApi() : AuthApiService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppExecutors(): AppExecutors = AppExecutors()

    @Provides
    @Singleton
    fun provideAuthRepository(
        authApi: AuthApiService,
        appExecutors: AppExecutors
    ): AuthRepository = AuthRepositoryImpl(authApi, appExecutors)

    @Provides
    @Singleton
    fun provideLoginUseCases(
        authRepository: AuthRepository,
        localAuthUserManager: LocalAuthUserManager
    ) : AuthUseCases {
        return AuthUseCases(
            login = LoginAction(authRepository),
            checkIsUserLogin = CheckIsUserLogin(localAuthUserManager = localAuthUserManager),
            saveUserInfoLogin = SaveUserInfoLogin(localAuthUserManager = localAuthUserManager),
            readUserAllToken = ReadUserAllToken(localAuthUserManager = localAuthUserManager)
        )
    }

}