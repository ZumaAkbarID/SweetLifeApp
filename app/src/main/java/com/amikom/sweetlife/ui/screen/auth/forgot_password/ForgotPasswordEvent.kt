package com.amikom.sweetlife.ui.screen.auth.forgot_password

sealed class ForgotPasswordEvent {
    data class ForgotPassword(val email: String) : ForgotPasswordEvent()
}