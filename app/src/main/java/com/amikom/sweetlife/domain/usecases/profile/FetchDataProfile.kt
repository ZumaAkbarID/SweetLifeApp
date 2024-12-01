package com.amikom.sweetlife.domain.usecases.profile

import androidx.lifecycle.LiveData
import com.amikom.sweetlife.data.model.DashboardModel
import com.amikom.sweetlife.data.model.ProfileModel
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.domain.repository.DashboardRepository
import com.amikom.sweetlife.domain.repository.ProfileRepository

class FetchDataProfile (
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(): LiveData<Result<ProfileModel>> {
        return profileRepository.fetchDataProfile()
    }
}