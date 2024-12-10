package com.amikom.sweetlife.data.remote.repository

import android.util.Log
import com.amikom.sweetlife.data.remote.dto.EditHealth.EditHealthResponse
import com.amikom.sweetlife.data.remote.json_request.EditHealthRequest
import com.amikom.sweetlife.data.remote.retrofit.FeatureApiService
import com.amikom.sweetlife.domain.repository.EditHealthDataRepository
import javax.inject.Inject


class EditHealthRepositoryImpl @Inject constructor(
    private val apiService: FeatureApiService
) : EditHealthDataRepository {

    override suspend fun updateHealth(health: EditHealthRequest): EditHealthResponse {
        try {
            val response = apiService.updateHealth(health)
            return if (response.isSuccessful) {
                response.body() ?: throw Exception("Empty response body")
            } else {
                throw Exception("API error: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("API", "Error: ${e.localizedMessage}")
            throw e
        }
    }
}