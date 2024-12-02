package com.amikom.sweetlife.data.remote.retrofit

import com.amikom.sweetlife.data.remote.dto.dashboard.DashboardResponse
import com.amikom.sweetlife.data.remote.dto.health_profile.HealthProfileResponse
import com.amikom.sweetlife.data.remote.dto.profile.ProfileResponse
import com.amikom.sweetlife.data.remote.dto.rekomen.RekomenResponse
import com.amikom.sweetlife.util.Constants
import retrofit2.Response
import retrofit2.http.GET

interface FeatureApiService {

    @GET("${Constants.API_VERSION}dashboard")
    suspend fun dashboard() : Response<DashboardResponse>

    @GET("${Constants.API_VERSION}users/profile")
    suspend fun getProfile() : Response<ProfileResponse>

    @GET("${Constants.API_VERSION}users/health")
    suspend fun getHealth() : Response<HealthProfileResponse>

    @GET("${Constants.API_VERSION}food-recomendation")
    suspend fun getRekomendasi() : Response<RekomenResponse>
}