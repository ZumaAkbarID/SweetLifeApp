package com.amikom.sweetlife.data.remote.json_request

data class HealthRequest(
    val height: Double,
    val weight: Double,
    val is_diabetic: Boolean,
    val smoking_history: String,
    val has_heart_disease: Boolean,
    val activity_level: String,

    val diabetic_type: String? = null,
    val insulin_level: Double? = null,
    val blood_pressure: Int? = null
)
