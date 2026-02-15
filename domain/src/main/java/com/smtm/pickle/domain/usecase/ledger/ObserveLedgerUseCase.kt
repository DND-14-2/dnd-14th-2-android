package com.smtm.pickle.domain.usecase.ledger

import com.smtm.pickle.domain.model.ledger.Ledger
import com.smtm.pickle.domain.model.ledger.LedgerId
import com.smtm.pickle.domain.repository.LedgerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveLedgerUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository,
) {

    operator fun invoke(id: LedgerId): Flow<Ledger> {
        return ledgerRepository.observeLedger(id.value)
    }
}
