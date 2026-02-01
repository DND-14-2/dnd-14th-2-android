package com.smtm.pickle.domain.usecase.auth

import com.smtm.pickle.domain.provider.TokenProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IsLoggedInUseCase @Inject constructor(
    private val tokenProvider: TokenProvider
) {
    operator fun invoke(): Flow<Boolean> =
        tokenProvider.getTokenFlow().map { it != null }
}
