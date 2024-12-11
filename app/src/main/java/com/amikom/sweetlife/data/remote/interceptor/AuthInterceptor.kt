package com.amikom.sweetlife.data.remote.interceptor

import android.util.Log
import com.amikom.sweetlife.data.model.NewTokenModel
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

        Log.d("BIJIX_INTERCEPTOR", "ACCESS: $accessToken\nREFRESH: $refreshToken")

        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        if (!accessToken.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $accessToken")
        }

        var response = chain.proceed(requestBuilder.build())

        if (response.code == 401 && !refreshToken.isNullOrEmpty()) {
            // Simpan response lama
            val oldResponse = response
            response = try {
                synchronized(lock) {
                    if (!isRefreshing) {
                        isRefreshing = true
                        var newTokenResult: Result<NewTokenModel>? = null
                        try {
                            newTokenResult = runBlocking { authRepository.refreshToken(refreshToken) }

                            if (newTokenResult is Result.Success) {
                                val newToken = newTokenResult.data
                                runBlocking { localAuthUserManager.saveNewTokenInfo(newToken) }

                                // Buat ulang request dengan token baru
                                val newRequest = originalRequest.newBuilder()
                                    .removeHeader("Authorization")
                                    .addHeader("Authorization", "Bearer ${newToken.accessToken}")
                                    .build()

                                // Tutup response lama setelah permintaan baru berhasil
                                oldResponse.close()
                                chain.proceed(newRequest)
                            } else {
                                oldResponse
                            }
                        } finally {
//                            if (newTokenResult !is Result.Success || newTokenResult.data.accessToken == "") {
//                                runBlocking { localAuthUserManager.logout() } // GAGAL TERUS ASUK
//                            }
                            isRefreshing = false
                        }
                    } else {
                        oldResponse
                    }
                }
            } catch (e: Exception) {
                oldResponse.close()
                Log.d("BIJIX_INTERCEPTOR", e.message.toString())
                throw e
            }
        }

        return response
    }
}
