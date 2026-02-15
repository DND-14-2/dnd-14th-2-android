package com.smtm.pickle.domain.usecase.ledger

import com.smtm.pickle.domain.common.utils.runSuspendCatching
import com.smtm.pickle.domain.model.ledger.LedgerCategory
import com.smtm.pickle.domain.model.ledger.LedgerType
import com.smtm.pickle.domain.model.ledger.PaymentMethod
import com.smtm.pickle.domain.repository.LedgerRepository
import java.time.LocalDate
import javax.inject.Inject

class EditLedgerUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository
) {

    suspend operator fun invoke(
        ledgerId: Long,
        amount: Long,
        type: LedgerType,
        category: LedgerCategory,
        description: String,
        occurredOn: LocalDate,
        paymentMethod: PaymentMethod,
        memo: String?,
    ) = runSuspendCatching {
        ledgerRepository.editLedger(
            ledgerId = ledgerId,
            amount = amount,
            type = type,
            category = category,
            description = description,
            occurredOn = occurredOn,
            paymentMethod = paymentMethod,
            memo = memo
        )
    }
}