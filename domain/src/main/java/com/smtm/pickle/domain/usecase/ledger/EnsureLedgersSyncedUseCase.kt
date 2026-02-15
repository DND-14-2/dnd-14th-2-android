package com.smtm.pickle.domain.usecase.ledger

import com.smtm.pickle.domain.common.utils.runSuspendCatching
import com.smtm.pickle.domain.repository.LedgerRepository
import java.time.YearMonth
import javax.inject.Inject

class EnsureLedgersSyncedUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository,
) {

    suspend operator fun invoke(
        baseMonth: YearMonth = YearMonth.now(),
        monthsBack: Long = 6,
        monthsForward: Long = 6,
    ): Result<Unit> = runSuspendCatching {
        val from = baseMonth.minusMonths(monthsBack).atDay(1)
        val to = baseMonth.plusMonths(monthsForward).atEndOfMonth()
        ledgerRepository.ensureSynced(from, to)
    }
}
