package com.amikom.sweetlife.ui.screen.History

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amikom.sweetlife.data.remote.dto.HistoryResponse.FoodHistory // Use the correct class
import com.amikom.sweetlife.data.remote.dto.HistoryResponse.HistoryResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.data.remote.dto.HistoryResponse.FoodLog

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _foodHistory = MutableLiveData<List<FoodHistory>?>()
    val foodHistory: MutableLiveData<List<FoodHistory>?> = _foodHistory

    fun fetchHistory() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            _error.postValue(null)

            val apiResult = historyRepository.fetchHistory()

            when (apiResult) {
                is Result.Success -> {
                    val logs = apiResult.data.data?.foodHistory // Ensure the correct field is used
                    _foodHistory.postValue(logs)
                    _isLoading.postValue(false)
                }

                is Result.Error -> {
                    _error.postValue(apiResult.error)
                    _foodHistory.postValue(emptyList())
                    _isLoading.postValue(false)
                }

                is Result.Empty -> {
                    _error.postValue("No food history available")
                    _foodHistory.postValue(emptyList())
                    _isLoading.postValue(false)
                }

                is Result.Loading -> {
                    _isLoading.postValue(true)
                }
            }
        }
    }
}
