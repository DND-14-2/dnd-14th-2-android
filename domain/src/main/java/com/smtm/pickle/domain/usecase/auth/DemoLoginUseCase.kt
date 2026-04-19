package com.smtm.pickle.domain.usecase.auth

import com.smtm.pickle.domain.common.utils.runSuspendCatching
import com.smtm.pickle.domain.model.auth.AuthToken
import com.smtm.pickle.domain.provider.DeviceIdProvider
import com.smtm.pickle.domain.repository.AuthRepository
import javax.inject.Inject

class DemoLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val deviceIdProvider: DeviceIdProvider,
) {
    suspend operator fun invoke(): Result<AuthToken> = runSuspendCatching {
        val deviceId = deviceIdProvider.getOrCreate()
        authRepository.demoLogin(deviceId = deviceId)
    }
}
