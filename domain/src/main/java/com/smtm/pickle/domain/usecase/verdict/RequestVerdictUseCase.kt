package com.smtm.pickle.domain.usecase.verdict

import com.smtm.pickle.domain.common.utils.runSuspendCatching
import com.smtm.pickle.domain.repository.VerdictRepository
import javax.inject.Inject

class RequestVerdictUseCase @Inject constructor(
    private val verdictRepository: VerdictRepository,
) {
    suspend operator fun invoke(ledgerEntryId: Long): Result<Unit> = runSuspendCatching {
        verdictRepository.requestVerdict(ledgerEntryId)
    }
}
