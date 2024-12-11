package com.amikom.sweetlife.ui.screen.assesment

import android.content.Context
import android.util.Log
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
import com.amikom.sweetlife.data.model.UpdateProfileModel
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.data.remote.json_request.HealthRequest
import com.amikom.sweetlife.domain.usecases.auth.AuthUseCases
import com.amikom.sweetlife.domain.usecases.profile.ProfileUseCases
import com.amikom.sweetlife.util.countAgeFromDate
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
    private val authUseCases: AuthUseCases,
    private val profileUseCases: ProfileUseCases
) : ViewModel() {

    private val _result = MutableStateFlow(false)
    val result: StateFlow<Boolean> = _result

    private val _showError = MutableStateFlow(false)
    val showError: StateFlow<Boolean> = _showError

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _updateProfileResult = MutableStateFlow<Result<Boolean>>(Result.Empty)
    val updateProfileResult: StateFlow<Result<Boolean>> = _updateProfileResult

    private val _createHealth = MutableStateFlow<Result<Boolean>>(Result.Empty)
    val createHealth: StateFlow<Result<Boolean>> = _createHealth

    private val _isUserLoggedIn = MutableStateFlow(true)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn

    init {
        viewModelScope.launch {
            authUseCases.checkIsUserLogin().collect { isLoggedIn ->
                _isUserLoggedIn.value = isLoggedIn
            }
        }
    }

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
    private fun validateAll(): Boolean {
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

    fun dismissError() {
        _showError.value = false
    }

    // Function to submit data to the server
    fun submitDataToServer() {
        viewModelScope.launch {
            _showError.value = false
            _errorMessage.value = ""

            if (!validateAll()) {
                _showError.value = true
                return@launch
            } else if (countAgeFromDate(personalData.dateOfBirth) < 1 || activityData1.weight < 1 || activityData1.height < 1) {
                _showError.value = true
                _errorMessage.value = "Invalid date of birth or weight or height"
                return@launch
            }

            val dataProfileUpdate = UpdateProfileModel(
                name = personalData.fullName,
                dateOfBirth = personalData.dateOfBirth,
                gender = personalData.gender,
            )

            Log.d("BIJIX_ASS", dataProfileUpdate.toString())

            profileUseCases.updateDataProfile(dataProfileUpdate).collect { result ->
                _updateProfileResult.value = result
            }

            val dataReadyToSend = HealthRequest(
                height = activityData1.height.toDouble(),
                weight = activityData1.weight.toDouble(),
                is_diabetic = diabetesStatus.isDiabetic,
                smoking_history = activityData2.smokingHistory.lowercase(),
                has_heart_disease = activityData2.heartDiseaseHistory == "Yes",
                activity_level = activityData1.physicalActivity.lowercase(),
                diabetic_type = if (diabetesStatus.isDiabetic) nextDiabetesStatus.type.trim().lowercase() else null,
                insulin_level = if (diabetesStatus.isDiabetic) nextDiabetesStatus.insulinLevel else null,
                blood_pressure = if (diabetesStatus.isDiabetic) nextDiabetesStatus.bloodPressure else null
            )

            Log.d("BIJIX_ASS", dataReadyToSend.toString())

            profileUseCases.createHealthProfile(dataReadyToSend).collect { result ->
                _createHealth.value = result
            }

            if (updateProfileResult.value is Result.Error) {
                _showError.value = true
                _errorMessage.value = "Can't update your profile, try again!"
            } else if (createHealth.value is Result.Error) {
                _showError.value = true
                _errorMessage.value = "Can't save your health data, please try again!"
            } else if (updateProfileResult.value is Result.Success && createHealth.value is Result.Success) {
                showToastMessage(context, "Saved!", Toast.LENGTH_LONG)
                authUseCases.saveHealthProfile(true)
                _result.emit(true)
            }
        }
    }
}