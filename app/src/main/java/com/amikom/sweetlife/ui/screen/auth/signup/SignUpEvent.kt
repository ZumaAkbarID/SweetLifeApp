package com.amikom.sweetlife.ui.screen.auth.signup

sealed class SignUpEvent {
    data class LoginProcess(val email: String, val password: String, val passwordConfirmation: String) : SignUpEvent()
}