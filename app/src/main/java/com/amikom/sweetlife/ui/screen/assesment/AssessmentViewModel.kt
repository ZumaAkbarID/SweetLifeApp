package com.amikom.sweetlife.ui.screen.assesment

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amikom.sweetlife.data.model.ActivityData
import com.amikom.sweetlife.data.model.DayGoals
import com.amikom.sweetlife.data.model.DiabetesStatus
import com.amikom.sweetlife.data.model.HealthHistory
import com.amikom.sweetlife.data.model.NextDiabetesStatus
import com.amikom.sweetlife.data.model.PersonalData
import com.amikom.sweetlife.domain.usecases.auth.AuthUseCases
import com.amikom.sweetlife.util.showToastMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel to handle the form data
@HiltViewModel
class AssessmentViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _result = MutableStateFlow(false)
    val result: StateFlow<Boolean> = _result

    // Track current data for each screen
    var personalData by mutableStateOf(PersonalData())
    var diabetesStatus by mutableStateOf(DiabetesStatus())
    var nextDiabetesStatus by mutableStateOf(NextDiabetesStatus())
    var activityData1 by mutableStateOf(ActivityData())
    var activityData2 by mutableStateOf(HealthHistory())
    var dayGoals by mutableStateOf(DayGoals())

    // Validation states
    var isPersonalDataValid by mutableStateOf(false)
    var isDiabetesStatusValid by mutableStateOf(false)
    var isNextDiabetesStatusValid by mutableStateOf(false)
    var isActivityDataValid by mutableStateOf(false)
    var isHealthHistoryValid by mutableStateOf(false)
    var isDayGoalsValid by mutableStateOf(false)

    // Validation logic
    fun validatePersonalData() {
        isPersonalDataValid = personalData.fullName.isNotBlank() &&
                personalData.dateOfBirth.isNotBlank() &&
                personalData.gender.isNotBlank()
    }

    fun validateDiabetesStatus() {
        isDiabetesStatusValid = diabetesStatus.isDiabetic != null
    }

    fun validateNextDiabetesStatus() {
        isNextDiabetesStatusValid = if (diabetesStatus.isDiabetic) {
            nextDiabetesStatus.type.isNotBlank() &&
                    nextDiabetesStatus.insulinLevel > 0 &&
                    nextDiabetesStatus.bloodPressure > 0
        } else {
            true // Skip validation if not diabetic
        }
    }

    fun validateActivityData() {
        isActivityDataValid = activityData1.height > 0 &&
                activityData1.weight > 0 &&
                activityData1.physicalActivity.isNotBlank()
    }

    fun validateHealthHistory() {
        isHealthHistoryValid = activityData2.smokingHistory.isNotBlank() &&
                activityData2.heartDiseaseHistory.isNotBlank()
    }

    fun validateDayGoals() {
        isDayGoalsValid = dayGoals.calories > 0 &&
                dayGoals.sugarIntake > 0
    }

    // Call all validations before submitting data
    fun validateAll(): Boolean {
        validatePersonalData()
        validateDiabetesStatus()
        validateNextDiabetesStatus()
        validateActivityData()
        validateHealthHistory()
        validateDayGoals()
        return isPersonalDataValid &&
                isDiabetesStatusValid &&
                isNextDiabetesStatusValid &&
                isActivityDataValid &&
                isHealthHistoryValid &&
                isDayGoalsValid
    }

    // Function to submit data to the server
    fun submitDataToServer() {
        viewModelScope.launch {
            if (!validateAll()) {
                // Handle validation failure
                return@launch
            }

            val dataReadyToSend = DataReadyToSend(
                height = activityData1.height.toDouble(),
                weight = activityData1.weight.toDouble(),
                isDiabetic = diabetesStatus.isDiabetic,
                smokingHistory = activityData2.smokingHistory,
                hasHeartDisease = activityData2.heartDiseaseHistory == "Yes",
                activityLevel = activityData1.physicalActivity,
                diabeticType = if (diabetesStatus.isDiabetic) nextDiabetesStatus.type else null,
                insulinLevel = if (diabetesStatus.isDiabetic) nextDiabetesStatus.insulinLevel else null,
                bloodPressure = if (diabetesStatus.isDiabetic) nextDiabetesStatus.bloodPressure else null
            )

            // Simulate API request
            _result.emit(true)
        }
    }
}

data class DataReadyToSend(
    val height: Double,
    val weight: Double,
    val isDiabetic: Boolean,
    val smokingHistory: String,
    val hasHeartDisease: Boolean,
    val activityLevel: String,

    val diabeticType: String?,
    val insulinLevel: Double?,
    val bloodPressure: Int?
)