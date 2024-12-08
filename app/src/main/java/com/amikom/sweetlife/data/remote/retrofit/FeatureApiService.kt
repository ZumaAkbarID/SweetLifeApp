package com.amikom.sweetlife.data.remote.retrofit

import com.amikom.sweetlife.data.remote.dto.dashboard.DashboardResponse
import com.amikom.sweetlife.data.remote.dto.health_profile.HealthProfileResponse
import com.amikom.sweetlife.data.remote.dto.profile.ProfileResponse
import com.amikom.sweetlife.data.remote.dto.rekomen.RekomenResponse
import com.amikom.sweetlife.data.remote.json_request.ProfileRequest
import com.amikom.sweetlife.util.Constants
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

interface FeatureApiService {

    @GET("${Constants.API_VERSION}dashboard")
    suspend fun dashboard(): Response<DashboardResponse>

    @GET("${Constants.API_VERSION}users/profile")
    suspend fun getProfile(): Response<ProfileResponse>

    @PUT("${Constants.API_VERSION}users/profile")
    suspend fun updateProfile(@Body profile: ProfileRequest): Response<ProfileResponse>

    @GET("${Constants.API_VERSION}users/health")
    suspend fun getHealth(): Response<HealthProfileResponse>

    @GET("${Constants.API_VERSION}food-recomendation")
    suspend fun getRekomendasi(): Response<RekomenResponse>

    @Multipart
    @PUT("${Constants.API_VERSION}users/profile")
    suspend fun uploadProfileImage(@Part image: MultipartBody.Part): ProfileResponse
}