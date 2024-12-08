package com.amikom.sweetlife.data.model

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
