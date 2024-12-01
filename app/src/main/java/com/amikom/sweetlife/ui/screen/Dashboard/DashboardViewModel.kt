package com.amikom.sweetlife.ui.screen.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amikom.sweetlife.data.model.DashboardModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.domain.usecases.auth.AuthUseCases
import com.amikom.sweetlife.domain.usecases.dashboard.DashboardUseCases
import com.amikom.sweetlife.domain.usecases.profile.ProfileUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardUseCases: DashboardUseCases,
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _isUserLoggedIn = MutableStateFlow(true)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn

    private val _dashboardState = MutableLiveData<Result<DashboardModel>>()
    val dashboardData: LiveData<Result<DashboardModel>> = _dashboardState

    init {
        viewModelScope.launch {
            authUseCases.checkIsUserLogin().collect { isLoggedIn ->
                _isUserLoggedIn.value = isLoggedIn
            }
        }
        fetchDashboard()
    }

    private fun fetchDashboard() {
        viewModelScope.launch {
            _dashboardState.postValue(Result.Loading)

            try {
                val result = dashboardUseCases.fetchData()

                result.observeForever { dashboardResult ->
                    _dashboardState.postValue(dashboardResult)
                }
            } catch (e: Exception) {
                _dashboardState.postValue(Result.Error(e.message ?: "Unexpected Error"))
            }
        }
    }
}

