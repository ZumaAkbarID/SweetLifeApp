package com.amikom.sweetlife.data.remote.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.amikom.sweetlife.data.remote.retrofit.FeatureApiService
import com.amikom.sweetlife.domain.repository.RekomenRepository
import com.amikom.sweetlife.data.remote.Result
import com.google.gson.Gson
import com.amikom.sweetlife.data.remote.dto.ErrorResponse
import com.amikom.sweetlife.data.remote.dto.rekomen.RekomenResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RekomenRepositoryImpl @Inject constructor(
    private val featureApiService: FeatureApiService
) : RekomenRepository {

    override suspend fun fetchRekomend(): Result<RekomenResponse> {
        return try {
            val response = featureApiService.getRekomendasi()
            if (response.isSuccessful) {
                val rekomenResponse = response.body()
                if (rekomenResponse != null) {
                    Result.Success(rekomenResponse)
                } else {
                    Result.Error("Data not found")
                }
            } else {
                val errorBody =
                    Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                val message = errorBody?.error ?: response.message()
                Log.e("RekomenRepositoryImpl", "Error: $message")
                Result.Error(message)
            }
        } catch (e: Exception) {
            Log.e("RekomenRepositoryImpl", "Error: ${e.message}")
            Result.Error("Failed to load rekomend: ${e.message}")
        }
    }
}
