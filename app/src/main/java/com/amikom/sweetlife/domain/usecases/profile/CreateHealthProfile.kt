package com.amikom.sweetlife.domain.usecases.profile

import androidx.lifecycle.LiveData
import com.amikom.sweetlife.data.model.DashboardModel
import com.amikom.sweetlife.data.model.ProfileModel
import com.amikom.sweetlife.data.model.UpdateProfileModel
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.data.remote.json_request.HealthRequest
import com.amikom.sweetlife.domain.repository.DashboardRepository
import com.amikom.sweetlife.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class CreateHealthProfile (
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(dataHealth: HealthRequest): Flow<Result<Boolean>> {
        return profileRepository.createHealthProfile(dataHealth)
    }
}