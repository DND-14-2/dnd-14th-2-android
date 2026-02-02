package com.smtm.pickle.domain.usecase.auth

import com.smtm.pickle.domain.model.auth.AuthToken
import com.smtm.pickle.domain.provider.TokenProvider
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(
    private val tokenProvider: TokenProvider
) {
    suspend operator fun invoke(token: AuthToken) {
        tokenProvider.saveToken(token)
    }
}
