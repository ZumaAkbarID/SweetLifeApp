package com.amikom.sweetlife.data.remote.dto.EditProfilePictResponse

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

data class EditProfilePictResponse(
    @SerializedName("status")
    val status: Boolean,

    @SerializedName("message")
    val message: String
)

data class Data(
    val file : Bitmap
)