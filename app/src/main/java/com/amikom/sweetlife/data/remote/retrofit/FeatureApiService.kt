package com.amikom.sweetlife.data.remote.retrofit

import com.amikom.sweetlife.data.remote.dto.dashboard.DashboardResponse
import com.amikom.sweetlife.util.Constants
import retrofit2.Response
import retrofit2.http.GET

interface FeatureApiService {

    @GET("${Constants.API_VERSION}dashboard")
    suspend fun dashboard() : Response<DashboardResponse>
}