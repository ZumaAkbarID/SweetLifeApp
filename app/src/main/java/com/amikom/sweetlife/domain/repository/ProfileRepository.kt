package com.amikom.sweetlife.domain.repository

import androidx.lifecycle.LiveData
import com.amikom.sweetlife.data.model.HealthProfileModel
import com.amikom.sweetlife.data.model.ProfileModel
import com.amikom.sweetlife.data.remote.Result

interface ProfileRepository {

    suspend fun fetchDataProfile(): LiveData<Result<ProfileModel>>
    suspend fun fetchHealthProfile(): LiveData<Result<HealthProfileModel>>

}