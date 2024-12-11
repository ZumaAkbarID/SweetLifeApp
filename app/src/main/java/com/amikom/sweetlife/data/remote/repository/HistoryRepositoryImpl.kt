package com.amikom.sweetlife.data.remote.repository

import android.util.Log
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.data.remote.dto.HistoryResponse.HistoryResponse
import com.amikom.sweetlife.data.remote.retrofit.FeatureApiService
import com.amikom.sweetlife.ui.screen.History.HistoryRepository
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val featureApiService: FeatureApiService
) : HistoryRepository {
    override suspend fun fetchHistory(): Result<HistoryResponse> {
        return try {
            val response = featureApiService.getHistory()

            if (response.isSuccessful) {
                val historyResponse = response.body()
                Log.d("HistoryRepositoryImpl", "Response body: $historyResponse")
                if (historyResponse != null) {
                    Result.Success(historyResponse)
                } else {
                    Result.Error("Data not found")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("HistoryRepositoryImpl", "Error code: ${response.code()}")
                Result.Error("Failed to load history: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("HistoryRepositoryImpl", "Error: ${e.message}", e)
            Result.Error("Failed to load history: ${e.message}")
        }
    }
}