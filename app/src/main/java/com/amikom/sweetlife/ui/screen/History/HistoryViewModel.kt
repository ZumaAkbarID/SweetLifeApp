package com.amikom.sweetlife.ui.screen.History

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyRepository: HistoryRepository
) : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _foodLogs = MutableLiveData<List<EntriesItem>>()
    val foodLogs: LiveData<List<EntriesItem>> get() = _foodLogs

    fun fetchHistory() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            _error.postValue(null)
            try {
                val entries = historyRepository.fetchEntries()
                _foodLogs.postValue(foodLogs.value?.plus(entries) ?: entries)
            } catch (e: Exception) {
                _error.postValue("Failed to load history: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

}