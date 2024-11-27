package com.amikom.sweetlife.ui.presentation.onboarding

import androidx.annotation.DrawableRes
import com.amikom.sweetlife.R

sealed class OnboardingModel(
    @DrawableRes val image: Int,
    val title: String,
    val description: String
) {
    data object FirstPages : OnboardingModel(
        image = R.drawable.welco1,
        title = "Welcome to sweetLife.",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting induy. Lorem Ipsum has been the industry's standard dummy "
    )
    data object SecondPages : OnboardingModel(
        image = R.drawable.welco2,
        title = "Safe and reliable to use.",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting induy. Lorem Ipsum has been the industry's standard dummy "
    )
    data object ThirdPages : OnboardingModel(
        image = R.drawable.welco3,
        title = "Can scan various types\nof food.",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting induy. Lorem Ipsum has been the industry's standard dummy "
    )
}