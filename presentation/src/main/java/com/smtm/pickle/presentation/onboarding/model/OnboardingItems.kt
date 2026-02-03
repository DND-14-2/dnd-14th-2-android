package com.smtm.pickle.presentation.onboarding.model

import com.smtm.pickle.presentation.R

sealed class OnboardingItems(
    val title: String,
    val description: String,
    val image: Int
) {
    object Step1 : OnboardingItems(
        title = "가계부 작성",
        description = "아주 간단하게\n가계부를 기록할 수 있어요",
        image = R.drawable.img_onboarding_step1
    )

    object Step2 : OnboardingItems(
        title = "소비 심판",
        description = "이 지출, 꼭 필요한가요?\n객관적으로 심판 받을 수 있어요",
        image = R.drawable.img_onboarding_step2
    )

    object Step3 : OnboardingItems(
        title = "지인 연동",
        description = "친구와 함께라면\n작심삼일 탈출!",
        image = R.drawable.img_onboarding_step3
    )
}
