package com.smtm.pickle.domain.usecase.user

import com.smtm.pickle.domain.common.utils.runSuspendCatching
import com.smtm.pickle.domain.repository.UserRepository
import javax.inject.Inject

class SetFirstLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(isFirstLogin: Boolean): Result<Unit> = runSuspendCatching {
        userRepository.setFirstLogin(isFirstLogin)
    }
}
