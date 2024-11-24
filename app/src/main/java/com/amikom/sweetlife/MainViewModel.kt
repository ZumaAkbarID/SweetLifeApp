package com.amikom.sweetlife

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amikom.sweetlife.domain.usecases.AppEntryUseCases
import com.amikom.sweetlife.ui.presentation.nvgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases
) : ViewModel() {
    var  splashCondition by mutableStateOf(true)
        private set

    private var _startDestination by mutableStateOf<Route>(Route.OnboardingScreen)
    val startDestination: Route get() = _startDestination

    init {
        appEntryUseCases.readAppEntry().onEach { shouldStartFromHomeScreen ->
            _startDestination = if (shouldStartFromHomeScreen) Route.HomeScreen else Route.OnboardingScreen
            delay(500L)
            splashCondition = false

        }.launchIn(viewModelScope)
    }
}