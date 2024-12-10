package com.amikom.sweetlife.data.remote.json_request

import com.google.gson.annotations.SerializedName

data class EditHealthRequest(
    @SerializedName("height")
    val height: Float, // Menggunakan Float karena data API mengindikasikan desimal

    @SerializedName("weight")
    val weight: Float, // Sama seperti height

    @SerializedName("is_diabetic")
    val isDiabetic: Boolean, // Boolean untuk true/false

    @SerializedName("smoking_history")
    val smokingHistory: String, // Enum-like field: "current", "never", "former", "ever"

    @SerializedName("has_heart_disease")
    val hasHeartDisease: Boolean,

    @SerializedName("activity_level")
    val activityLevel: String, // Enum-like field: "sedentary", "light", "moderate", "active", "very_active",

    // Optional fields
    @SerializedName("diabetic_type")
    val diabeticType: String? = null, // Optional, bisa null

    @SerializedName("insulin_level")
    val insulinLevel: Int? = null, // Optional, bisa null

    @SerializedName("blood_pressure")
    val bloodPressure: Int? = null, // Optional, bisa null
)
