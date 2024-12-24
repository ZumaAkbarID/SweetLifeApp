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
        title = "Welcome to SweetLife.",
        description = "Take control of your health with SweetLife, the app designed to monitor calories and support a healthier lifestyle."
    )

    data object SecondPages : OnboardingModel(
        image = R.drawable.welco2,
        title = "Safe and reliable to use.",
        description = "SweetLife provides accurate data and is safe to use, helping you manage your diet and daily activities."
    )

    data object ThirdPages : OnboardingModel(
        image = R.drawable.welco3,
        title = "Can scan various types\nof food.",
        description = "Scan different types of food with SweetLife and get nutritional information to support your health goals."
    )
}