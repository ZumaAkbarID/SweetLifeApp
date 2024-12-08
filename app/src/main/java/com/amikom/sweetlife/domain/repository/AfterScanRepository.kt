package com.amikom.sweetlife.domain.repository

import androidx.lifecycle.LiveData
import com.amikom.sweetlife.data.model.AfterScanModel

interface AfterScanRepository {

suspend fun fetchDataAfterScan(): LiveData<Result<AfterScanModel>>
}