package com.smtm.pickle.presentation.onboarding.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.smtm.pickle.presentation.R

enum class OnboardingItems(
    @get:StringRes val title: Int,
    @get:StringRes val description: Int,
    @get:DrawableRes val image: Int
) {
    Step1(
        title = R.string.onboarding_step1_title,
        description = R.string.onboarding_step1_description,
        image = R.drawable.img_onboarding_step1
    ),
    Step2(
        title = R.string.onboarding_step2_title,
        description = R.string.onboarding_step2_description,
        image = R.drawable.img_onboarding_step2
    ),
    Step3(
        title = R.string.onboarding_step3_title,
        description = R.string.onboarding_step3_description,
        image = R.drawable.img_onboarding_step3
    )
}
