package com.smtm.pickle.presentation.common.model.ledger

import androidx.annotation.StringRes
import com.smtm.pickle.domain.model.ledger.LedgerType
import com.smtm.pickle.presentation.R

enum class LedgerTypeUiModel(
    @StringRes val stringResId: Int,
) {
    Income(R.string.common_income),
    Expense(R.string.common_expense),
}

fun LedgerType.toUiModel(): LedgerTypeUiModel = when (this) {
    LedgerType.Income -> LedgerTypeUiModel.Income
    LedgerType.Expense -> LedgerTypeUiModel.Expense
}
