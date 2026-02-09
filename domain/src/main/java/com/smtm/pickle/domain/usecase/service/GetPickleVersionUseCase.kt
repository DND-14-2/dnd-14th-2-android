package com.smtm.pickle.domain.usecase.service

import com.smtm.pickle.domain.provider.VersionProvider
import javax.inject.Inject

class GetPickleVersionUseCase @Inject constructor(
    private val versionProvider: VersionProvider
) {
    operator fun invoke(): String = versionProvider.versionName
}
