package com.smtm.pickle.domain.usecase.onboarding

import com.smtm.pickle.domain.repository.UserRepository
import javax.inject.Inject

class SetOnboardingCompleteUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() {
        userRepository.setOnboardingCompleted(true)
    }
}
