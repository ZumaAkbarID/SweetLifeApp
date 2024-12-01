package com.amikom.sweetlife.di

import android.app.Application
import com.amikom.sweetlife.BuildConfig
import com.amikom.sweetlife.data.manager.LocalAuthUserManagerImpl
import com.amikom.sweetlife.data.manager.LocalUserManagerImpl
import com.amikom.sweetlife.data.remote.interceptor.AuthInterceptor
import com.amikom.sweetlife.data.remote.repository.AuthRepositoryImpl
import com.amikom.sweetlife.data.remote.repository.DashboardRepositoryImpl
import com.amikom.sweetlife.data.remote.repository.ProfileRepositoryImpl
import com.amikom.sweetlife.data.remote.retrofit.AuthApiService
import com.amikom.sweetlife.data.remote.retrofit.FeatureApiService
import com.amikom.sweetlife.domain.manager.LocalAuthUserManager
import com.amikom.sweetlife.domain.manager.LocalUserManager
import com.amikom.sweetlife.domain.manager.SessionManager
import com.amikom.sweetlife.domain.repository.AuthRepository
import com.amikom.sweetlife.domain.repository.DashboardRepository
import com.amikom.sweetlife.domain.repository.ProfileRepository
import com.amikom.sweetlife.domain.usecases.app_entry.AppEntryUseCases
import com.amikom.sweetlife.domain.usecases.app_entry.ReadAppEntry
import com.amikom.sweetlife.domain.usecases.app_entry.SaveAppEntry
import com.amikom.sweetlife.domain.usecases.auth.LoginAction
import com.amikom.sweetlife.domain.usecases.auth.AuthUseCases
import com.amikom.sweetlife.domain.usecases.auth.CheckIsUserLogin
import com.amikom.sweetlife.domain.usecases.auth.ForgotUserPassword
import com.amikom.sweetlife.domain.usecases.auth.ReadUserAllToken
import com.amikom.sweetlife.domain.usecases.auth.RefreshNewTokenAction
import com.amikom.sweetlife.domain.usecases.auth.RegisterAction
import com.amikom.sweetlife.domain.usecases.auth.SaveNewToken
import com.amikom.sweetlife.domain.usecases.auth.SaveUserInfoLogin
import com.amikom.sweetlife.domain.usecases.dashboard.DashboardUseCases
import com.amikom.sweetlife.domain.usecases.dashboard.FetchData
import com.amikom.sweetlife.domain.usecases.profile.FetchDataHealthProfile
import com.amikom.sweetlife.domain.usecases.profile.ProfileUseCases
import com.amikom.sweetlife.domain.usecases.profile.FetchDataProfile
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
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideFeatureApi(
        authInterceptor: AuthInterceptor,
    ) : FeatureApiService {
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(FeatureApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppExecutors(): AppExecutors = AppExecutors()

    @Provides
    @Singleton
    fun provideSessionManager(
        localAuthUserManager: LocalAuthUserManager
    ): SessionManager {
        return SessionManager(localAuthUserManager)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authApi: AuthApiService,
        appExecutors: AppExecutors
    ): AuthRepository = AuthRepositoryImpl(authApi, appExecutors)

    @Provides
    @Singleton
    fun provideDashboardRepository(
        featureApiService: FeatureApiService,
        appExecutors: AppExecutors
    ): DashboardRepository = DashboardRepositoryImpl(featureApiService, appExecutors)

    @Provides
    @Singleton
    fun provideProfileRepository(
        featureApiService: FeatureApiService,
        appExecutors: AppExecutors
    ): ProfileRepository = ProfileRepositoryImpl(featureApiService, appExecutors)

    @Provides
    @Singleton
    fun provideLoginUseCases(
        authRepository: AuthRepository,
        localAuthUserManager: LocalAuthUserManager
    ) : AuthUseCases {
        return AuthUseCases(
            login = LoginAction(authRepository = authRepository),
            checkIsUserLogin = CheckIsUserLogin(localAuthUserManager = localAuthUserManager),
            saveUserInfoLogin = SaveUserInfoLogin(localAuthUserManager = localAuthUserManager),
            readUserAllToken = ReadUserAllToken(localAuthUserManager = localAuthUserManager),
            register = RegisterAction(authRepository = authRepository),
            forgotPassword = ForgotUserPassword(authRepository = authRepository),
            refreshNewToken = RefreshNewTokenAction(authRepository = authRepository),
            saveNewToken = SaveNewToken(localAuthUserManager = localAuthUserManager)
        )
    }

    @Provides
    @Singleton
    fun provideDashboardUseCases(
        dashboardRepository: DashboardRepository,
    ) : DashboardUseCases {
        return DashboardUseCases(
            fetchData = FetchData(dashboardRepository = dashboardRepository)
        )
    }

    @Provides
    @Singleton
    fun provideProfileUseCases(
        profileRepository: ProfileRepository,
    ) : ProfileUseCases {
        return ProfileUseCases(
            fetchDataProfile = FetchDataProfile(profileRepository = profileRepository),
            fetchDataHealthProfile = FetchDataHealthProfile(profileRepository = profileRepository)
        )
    }

}