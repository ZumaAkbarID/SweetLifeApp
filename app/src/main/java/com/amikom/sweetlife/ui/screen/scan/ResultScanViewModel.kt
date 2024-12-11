package com.amikom.sweetlife.ui.screen.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amikom.sweetlife.data.model.DashboardModel
import com.amikom.sweetlife.data.model.FoodRequest
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.data.remote.dto.scan.FindFoodResponse
import com.amikom.sweetlife.data.remote.dto.scan.FoodListItem
import com.amikom.sweetlife.data.remote.dto.scan.SaveFoodResponse
import com.amikom.sweetlife.domain.usecases.auth.AuthUseCases
import com.amikom.sweetlife.domain.usecases.dashboard.DashboardUseCases
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultScanViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val dashboardUseCases: DashboardUseCases
) : ViewModel() {

    private val _isUserLoggedIn = MutableStateFlow(true)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn

    private val _findFoodState = MutableLiveData<Result<FindFoodResponse>>(Result.Empty)
    val findFoodData: LiveData<Result<FindFoodResponse>> = _findFoodState

    private val _saveFoodState = MutableLiveData<Result<SaveFoodResponse>>(Result.Empty)
    val saveFoodData: LiveData<Result<SaveFoodResponse>> = _saveFoodState

    init {
        viewModelScope.launch {
            authUseCases.checkIsUserLogin().collect { isLoggedIn ->
                _isUserLoggedIn.value = isLoggedIn
            }
        }
    }

    fun findFood(name: String, weight: Int) {
        viewModelScope.launch {
            _findFoodState.postValue(Result.Loading)

            try {
                val result = dashboardUseCases.findFood(name, weight)

                result.observeForever { findResult ->
                    _findFoodState.postValue(findResult)
                }
            } catch (e: Exception) {
                _findFoodState.postValue(Result.Error(e.message ?: "Unexpected Error"))
            }
        }
    }

    fun resetFindState() {
        _findFoodState.postValue(Result.Empty)
    }

    fun saveFood(listFood: FoodRequest) {
        viewModelScope.launch {
            _saveFoodState.postValue(Result.Loading)

            try {
                val result = dashboardUseCases.saveFood(listFood)

                result.observeForever { saveResult ->
                    _saveFoodState.postValue(saveResult)
                }
            } catch (e: Exception) {
                _saveFoodState.postValue(Result.Error(e.message ?: "Unexpected Error"))
            }
        }
    }
}