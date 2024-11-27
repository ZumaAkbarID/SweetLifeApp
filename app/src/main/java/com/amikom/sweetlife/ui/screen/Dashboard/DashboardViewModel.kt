package com.amikom.sweetlife.ui.screen.Dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: DashboardRepository
) : ViewModel() {

    private val _dashboardData = MutableLiveData<DashboardModel>()
    val dashboardData: LiveData<DashboardModel> = _dashboardData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchDashboard() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val data = repository.fetchDashboardData()
            _dashboardData.postValue(data)
            _isLoading.postValue(false)
        }
    }
}
