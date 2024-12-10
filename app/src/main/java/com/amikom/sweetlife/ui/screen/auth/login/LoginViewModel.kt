package com.amikom.sweetlife.ui.screen.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amikom.sweetlife.data.model.UserModel
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.domain.usecases.auth.AuthUseCases
import com.amikom.sweetlife.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
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
                Log.d("BIJIX_K", result.data.toString())
                viewModelScope.launch {
                    authUseCases.saveUserInfoLogin(result.data)
                    // Pastikan data tersimpan sek bolo
                    authUseCases.checkIsUserLogin().collect { isLoggedIn ->
                        _isUserLoggedIn.value = isLoggedIn
                        if (isLoggedIn) {
                            _isUserHasHealth.value = result.data.hasHealthProfile
                            delay(150)
                            _loginResult.value = result
                        }
                    }
                }
            } else {
                _loginResult.value = result
            }
        }
    }
}