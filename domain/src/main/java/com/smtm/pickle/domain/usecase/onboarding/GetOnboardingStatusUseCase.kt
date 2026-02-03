package com.smtm.pickle.domain.usecase.onboarding

import com.smtm.pickle.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOnboardingStatusUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return userRepository.isOnboardingCompleted()
    }
}
