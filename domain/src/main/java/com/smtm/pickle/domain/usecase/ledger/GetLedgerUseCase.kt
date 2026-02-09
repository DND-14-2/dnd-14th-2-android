package com.smtm.pickle.domain.usecase.ledger

import com.smtm.pickle.domain.model.ledger.Ledger
import com.smtm.pickle.domain.model.ledger.LedgerId
import com.smtm.pickle.domain.repository.LedgerRepository
import javax.inject.Inject

class GetLedgerUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository,
) {

    suspend operator fun invoke(id: LedgerId): Ledger? {
        return ledgerRepository.getLedger(id.value)
    }
}
