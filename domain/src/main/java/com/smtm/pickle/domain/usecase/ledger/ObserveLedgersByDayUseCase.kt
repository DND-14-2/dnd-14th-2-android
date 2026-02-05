package com.smtm.pickle.domain.usecase.ledger

import com.smtm.pickle.domain.model.ledger.Ledger
import com.smtm.pickle.domain.repository.LedgerRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class ObserveLedgersByDayUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository,
) {

    operator fun invoke(date: LocalDate): Flow<List<Ledger>> {
        return ledgerRepository.observeLedgers(date, date)
    }
}
