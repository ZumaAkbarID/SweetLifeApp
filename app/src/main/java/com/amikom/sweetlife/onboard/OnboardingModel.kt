package com.amikom.sweetlife.onboard

import androidx.annotation.DrawableRes
import com.amikom.sweetlife.R

sealed class OnboardingModel(
    @DrawableRes val image: Int,
    val title: String,
    val description: String
) {
    data object FirstPages : OnboardingModel(
        image = R.drawable.img_into_1,
        title = "welcome to sweetLife.",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting induy. Lorem Ipsum has been the industry's standard dummy "
    )
    data object SecondPages : OnboardingModel(
        image = R.drawable.img_into_2,
        title = "Aman dan terpercaya\nuntuk digunakan",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting induy. Lorem Ipsum has been the industry's standard dummy "
    )
    data object ThirdPages : OnboardingModel(
        image = R.drawable.img_into_3,
        title = "Dapat memindai berbagai\njenis makanan",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting induy. Lorem Ipsum has been the industry's standard dummy "
    )
}