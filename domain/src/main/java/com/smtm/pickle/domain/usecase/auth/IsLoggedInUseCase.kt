package com.smtm.pickle.domain.usecase.auth

import com.smtm.pickle.domain.common.utils.runSuspendCatching
import com.smtm.pickle.domain.provider.TokenProvider
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class IsLoggedInUseCase @Inject constructor(
    private val tokenProvider: TokenProvider
) {
    suspend operator fun invoke(): Result<Boolean> = runSuspendCatching {
        tokenProvider.getToken() != null
    }
}
