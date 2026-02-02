package com.smtm.pickle.domain.usecase.auth

import com.smtm.pickle.domain.model.auth.AuthToken
import com.smtm.pickle.domain.repository.AuthRepository
import javax.inject.Inject

class GoogleLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): Result<AuthToken> =
        authRepository.loginWithGoogle()
}
