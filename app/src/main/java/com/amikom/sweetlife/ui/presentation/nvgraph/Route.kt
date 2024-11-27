package com.amikom.sweetlife.ui.presentation.nvgraph

import kotlinx.serialization.Serializable

sealed class Route {

    @Serializable
    data object HomeScreen : Route()

    @Serializable
    data object OnboardingScreen : Route()

    @Serializable
    data object LoginScreen : Route()

    @Serializable
    data object AssesmentScreen : Route()

    @Serializable
    data class ProfileScreen(val id: Int) : Route()

    @Serializable
    data class HistoryScreen(val id: Int) : Route()

}