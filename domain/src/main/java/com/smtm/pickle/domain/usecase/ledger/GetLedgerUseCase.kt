package com.smtm.pickle.domain.usecase.ledger

import com.smtm.pickle.domain.common.utils.runSuspendCatching
import com.smtm.pickle.domain.model.ledger.LedgerId
import com.smtm.pickle.domain.repository.LedgerRepository
import javax.inject.Inject

class GetLedgerUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository,
) {

    suspend operator fun invoke(id: LedgerId) = runSuspendCatching {
        ledgerRepository.getLedger(id.value)
    }
}
