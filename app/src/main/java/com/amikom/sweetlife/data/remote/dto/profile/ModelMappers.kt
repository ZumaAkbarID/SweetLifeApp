package com.amikom.sweetlife.data.remote.dto.profile


import com.amikom.sweetlife.data.model.ProfileModel
import com.amikom.sweetlife.data.remote.dto.profile.ProfileResponse
import com.amikom.sweetlife.data.remote.json_request.ProfileRequest

// Mapping dari Response ke Model
fun ProfileResponse.toProfileModel(): ProfileModel {
    return ProfileModel(
        image = this.data?.photoProfile ?: "",
        id = this.data?.id ?: "",
        email = this.data?.email ?: "",
        name = this.data?.name ?: "",
        dateOfBirth = this.data?.dateOfBirth ?: "",
        gender = this.data?.gender ?: ""
    )
}

// Mapping dari Model ke Request
fun ProfileModel.toProfileRequest(): ProfileRequest {
    return ProfileRequest(
        image = this.image,
        name = this.name,
        email = this.email,
        dateOfBirth = this.dateOfBirth,
        gender = this.gender
    )
}

// Reverse mapping jika diperlukan
fun ProfileRequest.toProfileModel(): ProfileModel {
    return ProfileModel(
        image = this.image,
        name = this.name,
        email = this.email,
        dateOfBirth = this.dateOfBirth,
        gender = this.gender
    )
}