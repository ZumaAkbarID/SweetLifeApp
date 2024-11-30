package com.amikom.sweetlife.domain.nvgraph

import kotlinx.serialization.Serializable

sealed class Route {

    @Serializable
    data object HomeScreen : Route()

    @Serializable
    data object OnboardingScreen : Route()

    @Serializable
    data object LoginScreen : Route()

    @Serializable
    data object SignUpScreen : Route()

    @Serializable
    data object ForgotPasswordScreen: Route()

    @Serializable
    data object DashboardScreen: Route()

    @Serializable
    data object ProfileScreen: Route()
}