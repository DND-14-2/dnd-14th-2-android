package com.smtm.pickle.domain.usecase.ledger

import com.smtm.pickle.domain.common.utils.runSuspendCatching
import com.smtm.pickle.domain.model.ledger.LedgerId
import com.smtm.pickle.domain.repository.LedgerRepository
import javax.inject.Inject

class SyncLedgerUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository,
) {

    suspend operator fun invoke(ledgerId: LedgerId) = runSuspendCatching {
        ledgerRepository.syncLedger(ledgerId.value)
    }
}
