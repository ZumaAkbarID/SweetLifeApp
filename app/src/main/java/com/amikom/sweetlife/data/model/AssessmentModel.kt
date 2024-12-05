package com.amikom.sweetlife.data.model

// Data models for each screen
data class PersonalData(
    var fullName: String = "",
    var dateOfBirth: String = "",
    var gender: String = ""
)

data class DiabetesStatus(
    var isDiabetic: String = ""
)

data class ActivityData(
    var height: String = "",
    var weight: String = "",
    var physicalActivity: String = ""
)

data class HealthHistory(
    var smokingHistory: String = "",
    var heartDiseaseHistory: String = ""
)

data class DayGoals(
    var calories: String = "",
    var sugarIntake: String = ""
)