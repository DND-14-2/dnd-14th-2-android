package com.smtm.pickle.presentation.common.model.ledger

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.smtm.pickle.presentation.R

sealed class PaymentMethodUiModel(
    @StringRes val stringResId: Int,
    @DrawableRes val iconResId: Int,
) {
    data object BankTransfer : PaymentMethodUiModel(
        stringResId = R.string.ledger_payment_method_bank_transfer,
        iconResId = R.drawable.ic_ledger_payment_method_bank_transfer,
    )

    data object CreditCard : PaymentMethodUiModel(
        stringResId = R.string.ledger_payment_method_credit_card,
        iconResId = R.drawable.ic_ledger_payment_method_credit_card,
    )

    data object DebitCard : PaymentMethodUiModel(
        stringResId = R.string.ledger_payment_method_debit_card,
        iconResId = R.drawable.ic_ledger_payment_method_debit_card,
    )

    data object Cash : PaymentMethodUiModel(
        stringResId = R.string.ledger_payment_method_cash,
        iconResId = R.drawable.ic_ledger_payment_method_cash,
    )
}