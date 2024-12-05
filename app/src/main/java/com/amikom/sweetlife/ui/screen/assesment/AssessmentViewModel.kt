package com.amikom.sweetlife.ui.screen.assesment

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amikom.sweetlife.data.model.ActivityData
import com.amikom.sweetlife.data.model.DayGoals
import com.amikom.sweetlife.data.model.DiabetesStatus
import com.amikom.sweetlife.data.model.HealthHistory
import com.amikom.sweetlife.data.model.PersonalData
import kotlinx.coroutines.launch

// ViewModel to handle the form data
class AssessmentViewModel : ViewModel() {
    // Track current data for each screen
    var personalData by mutableStateOf(PersonalData())
    var diabetesStatus by mutableStateOf(DiabetesStatus())
    var activityData1 by mutableStateOf(ActivityData())
    var activityData2 by mutableStateOf(HealthHistory())
    var dayGoals by mutableStateOf(DayGoals())

    // Function to submit data to the server
    fun submitDataToServer() {
        viewModelScope.launch {
            // Combine all data into a single object or a network request here
            val allData = mapOf(
                "personalData" to personalData,
                "diabetesStatus" to diabetesStatus,
                "activityData1" to activityData1,
                "activityData2" to activityData2,
                "dayGoals" to dayGoals
            )

            // Send data to server
            // Here you can call your API service or network code
            // For example:
            // apiService.submitAssessment(allData)
        }
    }

    // Reset all data (optional)
    fun resetData() {
        personalData = PersonalData()
        diabetesStatus = DiabetesStatus()
        activityData1 = ActivityData()
        activityData2 = HealthHistory()
        dayGoals = DayGoals()
    }
}
