package com.amikom.sweetlife.ui.screen.profile.editProfile

import androidx.lifecycle.ViewModel
import com.amikom.sweetlife.data.model.ProfileModel
import com.amikom.sweetlife.data.remote.repository.ProfileRepositoryImpl
import com.amikom.sweetlife.data.remote.retrofit.FeatureApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EditProfileViewModel : ViewModel() {

    private val _data = MutableStateFlow(ProfileModel())
    val userProfile : StateFlow<ProfileModel> get() = _data

    private val _isLoading = MutableStateFlow(false)
    val isLoading : StateFlow<Boolean> get() = _isLoading

    private val _isError = MutableStateFlow(false)
    val isError : StateFlow<Boolean> get() = _isError




}