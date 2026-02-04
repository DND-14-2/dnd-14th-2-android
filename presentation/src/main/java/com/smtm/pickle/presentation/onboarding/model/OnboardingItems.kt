package com.smtm.pickle.presentation.onboarding.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.smtm.pickle.presentation.R

sealed class OnboardingItems(
    @get:StringRes val title: Int,
    @get:StringRes val description: Int,
    @get:DrawableRes val image: Int
) {
    object Step1 : OnboardingItems(
        title = R.string.onboarding_step1_title,
        description = R.string.onboarding_step1_description,
        image = R.drawable.img_onboarding_step1
    )

    object Step2 : OnboardingItems(
        title = R.string.onboarding_step2_title,
        description = R.string.onboarding_step2_description,
        image = R.drawable.img_onboarding_step2
    )

    object Step3 : OnboardingItems(
        title = R.string.onboarding_step3_title,
        description = R.string.onboarding_step3_description,
        image = R.drawable.img_onboarding_step3
    )

    companion object {
        val entries = listOf(Step1, Step2, Step3)
    }
}
