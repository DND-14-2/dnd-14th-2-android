package com.smtm.pickle.domain.usecase.onboarding

import com.smtm.pickle.domain.common.utils.runSuspendCatching
import com.smtm.pickle.domain.repository.UserRepository
import javax.inject.Inject

class SetOnboardingCompleteUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<Unit> = runSuspendCatching {
        userRepository.setOnboardingCompleted(true)
    }
}
