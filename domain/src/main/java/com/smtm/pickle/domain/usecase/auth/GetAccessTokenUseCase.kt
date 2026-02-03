package com.smtm.pickle.domain.usecase.auth

import com.smtm.pickle.domain.provider.TokenProvider
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val tokenProvider: TokenProvider
) {
    operator fun invoke(): String? = tokenProvider.getCachedToken()?.access
}

