package com.amikom.sweetlife.data.remote.retrofit

import com.amikom.sweetlife.data.remote.dto.LoginResponse
import com.amikom.sweetlife.data.remote.json_request.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface AuthApiService {

    @POST("/api/v1/auth/login")
    suspend fun login(

        @Body request: LoginRequest

    ) : Response<LoginResponse>

}