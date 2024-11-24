package com.amikom.sweetlife.ui.presentation.nvgraph

import kotlinx.serialization.Serializable

sealed class Route {

    @Serializable
    data object HomeScreen : Route()

    @Serializable
    data object OnboardingScreen : Route()
}