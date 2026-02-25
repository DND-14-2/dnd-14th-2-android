package com.smtm.pickle.domain.usecase.auth

import com.smtm.pickle.domain.common.utils.runSuspendCatching
import com.smtm.pickle.domain.provider.TokenProvider
import com.smtm.pickle.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val tokenProvider: TokenProvider,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit> = runSuspendCatching {
        authRepository.logout()
        tokenProvider.clearToken()
    }
}
