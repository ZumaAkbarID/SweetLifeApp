package com.amikom.sweetlife.ui.screen.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amikom.sweetlife.data.model.UserModel
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.domain.usecases.auth.AuthUseCases
import com.amikom.sweetlife.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _loginResult = MutableStateFlow<Result<UserModel>>(Result.Empty)
    val loginResult: StateFlow<Result<UserModel>> = _loginResult

    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn

    private val _isUserHasHealth = MutableStateFlow(false)
    val isUserHasHealth: StateFlow<Boolean> = _isUserHasHealth

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.LoginProcess -> {
                viewModelScope.launch {
                    loginProcess(event.email, event.password)
                }
            }
        }
    }

    private suspend fun loginProcess(email: String, password: String) {
        _loginResult.value = Result.Loading

        authUseCases.login(email, password).observeForever { result ->
            if (result is Result.Success<UserModel>) {
                viewModelScope.launch {
                    authUseCases.saveUserInfoLogin(result.data)
//                    Constants.CURRENT_TOKEN = result.data.token
                    // Pastikan data tersimpan sek bolo
                    authUseCases.checkIsUserLogin().collect { isLoggedIn ->
                        _isUserLoggedIn.value = isLoggedIn
                        if (isLoggedIn) {
                            authUseCases.checkHasHealthProfile().collect { hasHealth ->
                                _loginResult.value = result
                                _isUserHasHealth.value = hasHealth
                            }
                        }
                    }
                }
            } else {
                _loginResult.value = result
            }
        }
    }
}