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
    private val _foodLogs = MutableLiveData<List<EntriesItem>>()
    val foodLogs: LiveData<List<EntriesItem>> = _foodLogs

    fun fetchHistory() {
        viewModelScope.launch {
            val entries = historyRepository.fetchEntries()
            _foodLogs.postValue(foodLogs.value?.plus(entries) ?: entries)
        }
    }
}