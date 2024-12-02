package com.amikom.sweetlife.data.remote.interceptor

import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.domain.manager.LocalAuthUserManager
import com.amikom.sweetlife.domain.repository.AuthRepository
import com.amikom.sweetlife.util.Constants
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val localAuthUserManager: LocalAuthUserManager,
    private val authRepository: AuthRepository
) : Interceptor {

    @Volatile
    private var isRefreshing = false
    private val lock = Any()

    override fun intercept(chain: Interceptor.Chain): Response {
        val tokens = runBlocking { localAuthUserManager.getAllToken().first() }
        val accessToken = tokens.firstOrNull { it.first == Constants.USER_TOKEN }?.second
        val refreshToken = tokens.firstOrNull { it.first == Constants.USER_REFRESH_TOKEN }?.second

        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        if (!accessToken.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $accessToken")
        }

        val response = chain.proceed(requestBuilder.build())

        if (response.code == 401 && !refreshToken.isNullOrEmpty()) {
            synchronized(lock) {
                if (!isRefreshing) {
                    isRefreshing = true
                    try {
                        val newTokenResult =
                            runBlocking { authRepository.refreshToken(refreshToken) }

                        if (newTokenResult is Result.Success) {
                            val newToken = newTokenResult.data
                            runBlocking { localAuthUserManager.saveNewTokenInfo(newToken) }
                        }
                    } finally {
                        isRefreshing = false
                    }
                }
            }

            return chain.proceed(
                originalRequest.newBuilder()
                    .removeHeader("Authorization")
                    .addHeader("Authorization", "Bearer $accessToken")
                    .build()
            )
        }

        return response
    }

}
