package com.amikom.sweetlife.data.model

data class HealthProfileModel(
    val userId: String,
    val height: Double,
    val weight: Double,
    val isDiabetic: Boolean,
    val smokingHistory: String,
    val hasHeartDisease: Boolean,
    val activityLevel: String,
    val diabetesPrediction: DiabetesPrediction,
)

data class DiabetesPrediction(
    val riskPercentage: Int,
    val riskLevel: String,
    val note: String?
)