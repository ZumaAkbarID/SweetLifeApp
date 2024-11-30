package com.amikom.sweetlife.ui.screen.assesment

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NextDiabetesViewModel : ViewModel() {
    private val _selecetedDiabetType = MutableStateFlow("")
    val selectedDiabetType: StateFlow<String> = _selecetedDiabetType.asStateFlow()

    private val _diabetType = MutableStateFlow(
        listOf(
            "Didnt Know",
            "Type 1 Diabetes",
            "Type 2 Diabetes",
            "Gestational Diabetes"
        )
    )
    val diabetType: StateFlow<List<String>> = _diabetType.asStateFlow()

    fun updateSelectedType(type: String) {
        _selecetedDiabetType.value = type
    }
}