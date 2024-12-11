package com.amikom.sweetlife.domain.repository

import androidx.lifecycle.LiveData
import com.amikom.sweetlife.data.model.DashboardModel
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.data.remote.dto.scan.ScanResponse
import okhttp3.MultipartBody
import java.io.File

interface DashboardRepository {

    suspend fun fetchDataDashboard(): LiveData<Result<DashboardModel>>
    suspend fun scanFood(image:  MultipartBody.Part): LiveData<Result<ScanResponse>>

}