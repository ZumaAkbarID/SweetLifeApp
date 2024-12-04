package com.amikom.sweetlife.ui.screen.profile.editProfile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amikom.sweetlife.data.model.ProfileModel
import com.amikom.sweetlife.data.remote.json_request.ProfileRequest
import com.amikom.sweetlife.data.remote.repository.ProfileRepositoryImpl
import com.amikom.sweetlife.data.remote.retrofit.FeatureApiService
import com.amikom.sweetlife.domain.repository.EditProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val repository: EditProfileRepository
) : ViewModel() {

    private val _userProfile = MutableLiveData(ProfileModel())
    val userProfile: LiveData<ProfileModel> = _userProfile

    private val _updateStatus = MutableLiveData<Boolean>()
    val updateStatus: LiveData<Boolean> = _updateStatus

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _selectedGender = MutableStateFlow("")
    val selectedGender: StateFlow<String> = _selectedGender.asStateFlow()

    fun onNameChange(newName: String) {
        _userProfile.value = _userProfile.value?.copy(name = newName)
    }

    fun onEmailChange(newEmail: String) {
        _userProfile.value = _userProfile.value?.copy(email = newEmail)
    }

    fun onDateChange(newDate: String) {
        _userProfile.value = _userProfile.value?.copy(dateOfBirth = newDate)
    }

    fun onGenderChange(newGender: String) {
        _userProfile.value = _userProfile.value?.copy(gender = newGender)
    }

    val GenderOptions = listOf("Female", "Male")

    fun updateSelectedType(type: String) {
        _selectedGender.value = type
    }

    fun updateProfile() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val profileRequest = ProfileRequest(
                    name = _userProfile.value?.name.orEmpty(),
                    email = _userProfile.value?.email.orEmpty(),
                    dateOfBirth = _userProfile.value?.dateOfBirth.orEmpty(),
                    gender = _userProfile.value?.gender.orEmpty()
                )
                val response = repository.updateProfile(profileRequest)
                _updateStatus.value = response.status
            } catch (e: Exception) {
                Log.e("EditProfileViewModel", "updateProfile: ${e.message}")
                _updateStatus.value = false
            } finally {
                Log.d("EditProfileViewModel", "updateProfile: finally")
                _loading.value = false
            }
        }
    }
}

