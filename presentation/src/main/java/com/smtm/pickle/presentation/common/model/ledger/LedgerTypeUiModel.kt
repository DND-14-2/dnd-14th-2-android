package com.smtm.pickle.presentation.common.model.ledger

import androidx.annotation.StringRes
import com.smtm.pickle.presentation.R

sealed class LedgerTypeUiModel(
    @StringRes val stringResId: Int,
) {
    data object Income : LedgerTypeUiModel(R.string.common_income)
    data object Expense : LedgerTypeUiModel(R.string.common_expense)
}