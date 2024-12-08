package com.amikom.sweetlife.data.remote.json_request

import com.google.gson.annotations.SerializedName

data class ProfileRequest(
    @SerializedName("image") val image: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("date_of_birth") val dateOfBirth: String,
    @SerializedName("gender") val gender: String
)
