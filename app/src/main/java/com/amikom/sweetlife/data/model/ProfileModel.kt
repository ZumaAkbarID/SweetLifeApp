package com.amikom.sweetlife.data.model

import android.graphics.Bitmap

data class ProfileModel(
    val image : String = "",
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val dateOfBirth: String = "",
    val gender: String = "",
)

data class EditProfileModel(
    val image : String = "",
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val dateOfBirth: String,
    val gender: String = "",
)

data class UpdateProfileModel(
    val name: String = "",
    val dateOfBirth: String = "",
    val gender: String = "",
    val profilePicture: Bitmap? = null
)