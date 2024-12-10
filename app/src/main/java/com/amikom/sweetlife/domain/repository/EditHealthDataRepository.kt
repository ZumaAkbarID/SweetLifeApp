package com.amikom.sweetlife.domain.repository

import com.amikom.sweetlife.data.remote.dto.EditHealth.EditHealthResponse
import com.amikom.sweetlife.data.remote.dto.profile.ProfileResponse
import com.amikom.sweetlife.data.remote.json_request.EditHealthRequest
import com.amikom.sweetlife.data.remote.json_request.ProfileRequest
import okhttp3.MultipartBody
import javax.inject.Inject


interface EditHealthDataRepository{
    suspend fun updateHealth(Health: EditHealthRequest): EditHealthResponse
}