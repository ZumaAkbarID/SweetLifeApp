package com.amikom.sweetlife.domain.usecases.auth

data class AuthUseCases(
    val login: LoginAction,
    val checkIsUserLogin: CheckIsUserLogin,
    val readUserAllToken: ReadUserAllToken,
    val saveUserInfoLogin: SaveUserInfoLogin,

    val register: RegisterAction,

    val forgotPassword: ForgotUserPassword,

    val refreshNewToken: RefreshNewTokenAction,
    val saveNewToken: SaveNewToken,
)