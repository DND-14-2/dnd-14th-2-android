package com.smtm.pickle.presentation.common.model.ledger

import com.smtm.pickle.domain.model.ledger.Ledger
import java.time.LocalDate

data class LedgerUiModel(
    val id: Long,
    val type: LedgerTypeUiModel,
    val amount: Long,
    val category: CategoryUiModel,
    val description: String,
    val occurredOn: LocalDate,
    val paymentMethod: PaymentMethodUiModel,
    val memo: String?
)

fun Ledger.toUiModel(): LedgerUiModel = LedgerUiModel(
    id = id.value,
    type = type.toUiModel(),
    amount = amount.value,
    category = category.toUiModel(),
    description = description,
    occurredOn = occurredOn,
    paymentMethod = paymentMethod.toUiModel(),
    memo = memo
)
