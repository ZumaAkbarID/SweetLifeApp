package com.amikom.sweetlife.ui.screen.auth.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amikom.sweetlife.data.model.UserModel
import com.amikom.sweetlife.domain.usecases.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.domain.nvgraph.Route
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _signUpResult = MutableStateFlow<Result<Boolean>>(Result.Empty)
    val signUpResult: StateFlow<Result<Boolean>> = _signUpResult

    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.LoginProcess -> {
                if(event.password != event.passwordConfirmation) {
                    _signUpResult.value = Result.Error("Passwords do not match")
                } else {
                    viewModelScope.launch {
                        signUpProcess(event.email, event.password)
                    }
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            authUseCases.checkIsUserLogin().collect { isLoggedIn ->
                _isUserLoggedIn.value = isLoggedIn
            }
        }
    }

    private suspend fun signUpProcess(email: String, password: String) {
        _signUpResult.value = Result.Loading

        authUseCases.register(email, password).observeForever { result ->
            _signUpResult.value = result
        }
    }
}