package com.amikom.sweetlife.domain.repository

import androidx.lifecycle.LiveData
import com.amikom.sweetlife.data.model.DashboardModel
import com.amikom.sweetlife.data.remote.Result

interface DashboardRepository {

    suspend fun fetchDataDashboard(): LiveData<Result<DashboardModel>>

}