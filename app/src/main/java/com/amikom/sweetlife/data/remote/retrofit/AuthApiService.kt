package com.amikom.sweetlife.data.remote.retrofit

import com.amikom.sweetlife.data.remote.dto.LoginResponse
import com.amikom.sweetlife.data.remote.dto.RegisterResponse
import com.amikom.sweetlife.data.remote.json_request.LoginRequest
import com.amikom.sweetlife.data.remote.json_request.RegisterRequest
import com.amikom.sweetlife.util.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("${Constants.API_VERSION}auth/login")
    suspend fun login(

        @Body request: LoginRequest

    ) : Response<LoginResponse>

    @POST("${Constants.API_VERSION}auth/register")
    suspend fun register(

        @Body request: RegisterRequest

    ) : Response<RegisterResponse>

}