package com.amikom.sweetlife

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amikom.sweetlife.domain.usecases.app_entry.AppEntryUseCases
import com.amikom.sweetlife.domain.nvgraph.Route
import com.amikom.sweetlife.domain.usecases.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases,
    private val authUseCases: AuthUseCases
) : ViewModel() {
    var splashCondition by mutableStateOf(true)
        private set

    private var _startDestination by mutableStateOf<Route>(Route.OnboardingScreen)
    val startDestination: Route get() = _startDestination

    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn

    init {
        viewModelScope.launch {
            authUseCases.checkIsUserLogin().collect { isLoggedIn ->
                _isUserLoggedIn.value = isLoggedIn

                appEntryUseCases.readAppEntry().collect { shouldStartFromHomeScreen ->
                    _startDestination = when {
                        isUserLoggedIn.value && shouldStartFromHomeScreen -> Route.HomeScreen
                        !isUserLoggedIn.value && shouldStartFromHomeScreen -> Route.LoginScreen
                        isUserLoggedIn.value && !shouldStartFromHomeScreen -> Route.HomeScreen
                        else -> Route.OnboardingScreen
                    }

                    delay(500L)
                    splashCondition = false
                }
            }
        }
    }
}