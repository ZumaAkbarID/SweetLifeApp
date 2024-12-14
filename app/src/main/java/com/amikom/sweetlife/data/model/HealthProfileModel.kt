package com.amikom.sweetlife.data.model

import com.google.gson.annotations.SerializedName

data class HealthProfileModel(
    val userId: String,
    val height: Double,
    val weight: Double,
    @SerializedName("is_diabetic")
    val isDiabetic: Boolean,
    @SerializedName("smoking_history")
    val smokingHistory: String,
    @SerializedName("has_heart_disease")
    val hasHeartDisease: Boolean,
    @SerializedName("activity_level")
    val activityLevel: String,
    @SerializedName("diabetes_details")
    val diabetesDetails: DiabetesDetails? = null, // Menangani data nested
    val diabetesPrediction: DiabetesPrediction? = null
)

data class DiabetesDetails(
    @SerializedName("diabetic_type")
    val diabeticType: String? = null, // Tipe diabetes (opsional)
    @SerializedName("insulin_level")
    val insulinLevel: Double? = null, // Level insulin (opsional)
    @SerializedName("blood_pressure")
    val bloodPressure: Int? = null // Tekanan darah (opsional)
)

data class DiabetesPrediction(
    val riskPercentage: Double,
    val riskLevel: String,
    val note: String? = null
)
