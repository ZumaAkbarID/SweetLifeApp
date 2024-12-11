package com.amikom.sweetlife.ui.screen.History

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amikom.sweetlife.data.remote.dto.HistoryResponse.FoodLog
import com.amikom.sweetlife.data.remote.dto.HistoryResponse.HistoryResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.amikom.sweetlife.data.remote.Result

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _foodHistory = MutableLiveData<List<FoodLog>?>()
    val foodLogs: MutableLiveData<List<FoodLog>?> = _foodHistory

    fun fetchHistory() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            _error.postValue(null)

            val apiResult = historyRepository.fetchHistory()

            when (apiResult) {
                is Result.Success -> {
                    val logs = apiResult.data.foodLogs?.toList() ?: emptyList()
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