package com.amikom.sweetlife.ui.screen.assesment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HowActivityVIewModel : ViewModel() {
    private val _selectedActivityType = MutableStateFlow("")
    val selectedActivityType: StateFlow<String> = _selectedActivityType.asStateFlow()

    private val _activityType = MutableStateFlow(listOf(
        "Type 1 Diabetes",
        "Type 2 Diabetes",
        "Gestational Diabetes"
    ))
    val activityTypes: StateFlow<List<String>> = _activityType.asStateFlow()

    fun updateSelectedType(type: String) {
        _selectedActivityType.value = type
    }
}