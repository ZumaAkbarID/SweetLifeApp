package com.amikom.sweetlife.domain.usecases.dashboard

import androidx.lifecycle.LiveData
import com.amikom.sweetlife.data.model.DashboardModel
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.data.remote.dto.scan.FindFoodResponse
import com.amikom.sweetlife.domain.repository.DashboardRepository

class FindFood (
    private val dashboardRepository: DashboardRepository
) {
    suspend operator fun invoke(name: String, weight: Int): LiveData<Result<FindFoodResponse>> {
        return dashboardRepository.fetchFindFood(name, weight)
    }
}