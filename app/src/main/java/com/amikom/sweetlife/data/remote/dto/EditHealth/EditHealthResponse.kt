package com.amikom.sweetlife.data.remote.dto.EditHealth

import com.amikom.sweetlife.data.remote.dto.profile.Data
import com.google.gson.annotations.SerializedName

data class EditHealthResponse(
    @SerializedName("status")
    val status: Boolean? = null,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("data")
    val data: Data? = null,
)

data class Data(
    @SerializedName("height")
    val height: Float, // Tinggi badan dalam desimal

    @SerializedName("weight")
    val weight: Float, // Berat badan dalam desimal

    @SerializedName("is_diabetic")
    val isDiabetic: Boolean, // Apakah memiliki diabetes

    @SerializedName("smoking_history")
    val smokingHistory: String, // "never", "current", dll.

    @SerializedName("has_heart_disease")
    val hasHeartDisease: Boolean, // Apakah memiliki penyakit jantung

    @SerializedName("activity_level")
    val activityLevel: String, // Tingkat aktivitas

    @SerializedName("diabetes_details")
    val diabetesDetails: DiabetesDetails? = null, // Detil diabetes (opsional)
)

data class DiabetesDetails(
    @SerializedName("diabetic_type")
    val diabeticType: String?, // Tipe diabetes (opsional)

    @SerializedName("insulin_level")
    val insulinLevel: Double?, // Level insulin (opsional)

    @SerializedName("blood_pressure")
    val bloodPressure: Int?, // Tekanan darah (opsional)
)
