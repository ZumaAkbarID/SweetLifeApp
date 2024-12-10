package com.amikom.sweetlife.data.remote.repository

import androidx.lifecycle.LiveData
import com.amikom.sweetlife.data.model.AfterScanModel
import com.amikom.sweetlife.data.remote.retrofit.FeatureApiService
import com.amikom.sweetlife.domain.repository.AfterScanRepository
import javax.inject.Inject

class AfterScanRepositoryImpl @Inject constructor(
    private val featureApiService: FeatureApiService
) : AfterScanRepository {
    override suspend fun fetchDataAfterScan(): LiveData<Result<AfterScanModel>> {
        TODO("Not yet implemented")
    }
}