package com.amikom.sweetlife.ui.screen.profile.EditHealthData

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amikom.sweetlife.data.model.HealthProfileModel
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.data.remote.dto.EditHealth.EditHealthResponse
import com.amikom.sweetlife.data.remote.json_request.EditHealthRequest
import com.amikom.sweetlife.domain.repository.EditHealthDataRepository
import com.amikom.sweetlife.domain.usecases.profile.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditDataScreenViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    private val repository: EditHealthDataRepository
) : ViewModel() {
    //digunakan untuk mengupdate data kesehatan
    private val _healthDataState = MutableStateFlow<HealthDataState>(HealthDataState.Initial)
    val healthDataState: StateFlow<HealthDataState> = _healthDataState.asStateFlow()

    // digunakan untuk menampilkan data kesehatan
    private val _healthState = MutableLiveData<Result<HealthProfileModel>>()
    val healthData: LiveData<Result<HealthProfileModel>> = _healthState

    fun updateHealth(editHealthRequest: EditHealthRequest) {
        viewModelScope.launch {
            _healthDataState.value = HealthDataState.Loading
            try {
                val response = repository.updateHealth(editHealthRequest)
                _healthDataState.value = HealthDataState.Success(response)
            } catch (e: Exception) {
                _healthDataState.value = HealthDataState.Error(
                    e.localizedMessage ?: "Gagal mengupdate data kesehatan"
                )
            }
        }
    }

    fun fetchHealthData() {
        viewModelScope.launch {
            _healthState.postValue(Result.Loading)
            try {
                val result = profileUseCases.fetchDataHealthProfile()
                result.observeForever { healthResult ->
                    Log.d("HealthData", healthResult.toString())
                    _healthState.postValue(healthResult)
                }
            } catch (e: Exception) {
                _healthState.postValue(Result.Error(e.message ?: "Unexpected Error"))
            }
        }
    }
}


sealed class HealthDataState {
    object Initial : HealthDataState()
    object Loading : HealthDataState()
    data class Success(val data: EditHealthResponse) : HealthDataState()
    data class Error(val message: String) : HealthDataState()
}

data class EditHealthData(
    val height: Float?,
    val weight: Float?,
    val diabetesStatus: String?,
    val smokingStatus: String?,
    val bloodSugarLevel: Int?,
    val heartDisease: String?,
    val activityLevel: String?
)
