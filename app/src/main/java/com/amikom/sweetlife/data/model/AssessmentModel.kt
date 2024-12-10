package com.amikom.sweetlife.data.model

// Data models for each screen
data class PersonalData(
    var fullName: String = "",
    var dateOfBirth: String = "",
    var gender: String = ""
)

data class DiabetesStatus(
    var isDiabetic: Boolean = false
)

data class NextDiabetesStatus(
    var type: String = "",
    var insulinLevel: Double = 0.0,
    var bloodPressure: Int = 0
)

data class ActivityData(
    var height: Int = 0,
    var weight: Int = 0,
    var physicalActivity: String = ""
)

data class HealthHistory(
    var smokingHistory: String = "",
    var heartDiseaseHistory: String = ""
)

data class DayGoals(
    var calories: Int = 0,
    var sugarIntake: Int = 0
)