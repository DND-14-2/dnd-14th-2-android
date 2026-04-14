package com.smtm.pickle.presentation.verdict.model

import com.smtm.pickle.domain.model.verdict.LedgerEntryInfo
import com.smtm.pickle.presentation.common.model.ledger.CategoryUiModel
import com.smtm.pickle.presentation.common.model.ledger.PaymentMethodUiModel
import com.smtm.pickle.presentation.common.model.ledger.toUiModel

/** 소비 내역 경량 UI 모델 */
data class LedgerEntryUiModel(
    val id: Long,
    val amount: Long,
    val category: CategoryUiModel,
    val paymentMethod: PaymentMethodUiModel,
    val description: String,
)

fun LedgerEntryInfo.toUiModel() = LedgerEntryUiModel(
    id = id,
    amount = amount,
    category = category.toUiModel(),
    paymentMethod = paymentMethod.toUiModel(),
    description = description,
)
