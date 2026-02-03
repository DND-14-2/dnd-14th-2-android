package com.smtm.pickle.domain.usecase.auth

import com.smtm.pickle.domain.provider.TokenProvider
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val tokenProvider: TokenProvider
) {
    suspend operator fun invoke() {
        tokenProvider.clearToken()
    }
}
