package com.amikom.sweetlife.domain.usecases.dashboard

import androidx.lifecycle.LiveData
import com.amikom.sweetlife.data.model.DashboardModel
import com.amikom.sweetlife.data.model.FoodRequest
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.data.remote.dto.scan.FindFoodResponse
import com.amikom.sweetlife.data.remote.dto.scan.SaveFoodResponse
import com.amikom.sweetlife.domain.repository.DashboardRepository

class SaveFood (
    private val dashboardRepository: DashboardRepository
) {
    suspend operator fun invoke(listFood: FoodRequest): LiveData<Result<SaveFoodResponse>> {
        return dashboardRepository.saveFoodRequest(listFood)
    }
}