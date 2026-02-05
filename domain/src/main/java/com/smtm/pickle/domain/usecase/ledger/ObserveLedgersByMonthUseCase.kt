package com.smtm.pickle.domain.usecase.ledger

import com.smtm.pickle.domain.model.ledger.Ledger
import com.smtm.pickle.domain.repository.LedgerRepository
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth
import javax.inject.Inject

class ObserveLedgersByMonthUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository,
) {

    operator fun invoke(
        yearMonth: YearMonth,
        backwardMonths: Long = 1L,
        forwardMonths: Long = 1L,
    ): Flow<List<Ledger>> {
        val from = yearMonth.minusMonths(backwardMonths).atDay(1)
        val to = yearMonth.plusMonths(forwardMonths).atEndOfMonth()
        return ledgerRepository.observeLedgers(from, to)
    }
}
