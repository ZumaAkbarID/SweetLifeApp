package com.amikom.sweetlife.domain.repository

import androidx.lifecycle.LiveData
import com.amikom.sweetlife.data.model.HealthProfileModel
import com.amikom.sweetlife.data.model.ProfileModel
import com.amikom.sweetlife.data.model.UpdateProfileModel
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.data.remote.dto.profile.ProfileResponse
import com.amikom.sweetlife.data.remote.json_request.HealthRequest
import com.amikom.sweetlife.domain.usecases.profile.UpdateDataProfile
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface ProfileRepository {

    suspend fun fetchDataProfile(): LiveData<Result<ProfileModel>>
    suspend fun fetchHealthProfile(): LiveData<Result<HealthProfileModel>>
    suspend fun updateDataProfile(dataProfile: UpdateProfileModel): Flow<Result<Boolean>>
    suspend fun createHealthProfile(dataHealth: HealthRequest): Flow<Result<Boolean>>

}