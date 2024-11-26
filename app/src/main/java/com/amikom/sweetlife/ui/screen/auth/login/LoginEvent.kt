package com.amikom.sweetlife.ui.screen.auth.login

sealed class LoginEvent {
    data class LoginProcess(val email: String, val password: String) : LoginEvent()
}