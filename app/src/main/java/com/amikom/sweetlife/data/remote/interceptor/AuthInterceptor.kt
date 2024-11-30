package com.amikom.sweetlife.data.remote.interceptor

import android.util.Log
import com.amikom.sweetlife.data.model.UserModel
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

    override fun intercept(chain: Interceptor.Chain): Response {
        //  token dari LocalAuthUserManager
        val tokens = runBlocking { localAuthUserManager.getAllToken().first() }
        val accessToken = tokens.firstOrNull { it.first == Constants.USER_TOKEN }?.second
        val refreshToken = tokens.firstOrNull { it.first == Constants.USER_REFRESH_TOKEN }?.second

        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        // header Authorization
        if (!accessToken.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $accessToken")
        }

        val response = chain.proceed(requestBuilder.build())

        // Cek response 401 (Unauthorized)
        if (response.code == 401 && !refreshToken.isNullOrEmpty()) {
            response.close() // Tutup response lama

            val newTokenResult = runBlocking { authRepository.refreshToken(refreshToken).value }

            if (newTokenResult is Result.Success) {
                // Simpan token baru
                val newToken = newTokenResult.data
                runBlocking {
                    localAuthUserManager.saveNewTokenInfo(newToken)
                }

                Log.d("NEW_TOKEN", newToken.accessToken)

                // Kirim ulang request dengan token baru
                val newRequest = originalRequest.newBuilder()
                    .removeHeader("Authorization")
                    .addHeader("Authorization", "Bearer ${newToken.accessToken}")
                    .build()
                return chain.proceed(newRequest)
            } else {
                // Jika gagal refresh, logout dan lempar exception
                runBlocking { localAuthUserManager.saveInfoLogin(UserModel("", "", "", "", "", false)) }
                throw Exception("Token refresh failed. Please login again.")
            }
        }

        return response
    }
}
