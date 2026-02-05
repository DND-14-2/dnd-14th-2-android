package com.smtm.pickle.presentation.common.model.ledger

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.smtm.pickle.domain.model.ledger.PaymentMethod
import com.smtm.pickle.presentation.R

enum class PaymentMethodUiModel(
    @StringRes val stringResId: Int,
    @DrawableRes val iconResId: Int,
) {
    BankTransfer(
        stringResId = R.string.ledger_payment_method_bank_transfer,
        iconResId = R.drawable.ic_ledger_payment_method_bank_transfer,
    ),
    CreditCard(
        stringResId = R.string.ledger_payment_method_credit_card,
        iconResId = R.drawable.ic_ledger_payment_method_credit_card,
    ),
    DebitCard(
        stringResId = R.string.ledger_payment_method_debit_card,
        iconResId = R.drawable.ic_ledger_payment_method_debit_card,
    ),
    Cash(
        stringResId = R.string.ledger_payment_method_cash,
        iconResId = R.drawable.ic_ledger_payment_method_cash,
    ),
}


fun PaymentMethod.toUiModel(): PaymentMethodUiModel = when (this) {
    PaymentMethod.BankTransfer -> PaymentMethodUiModel.BankTransfer
    PaymentMethod.CreditCard -> PaymentMethodUiModel.CreditCard
    PaymentMethod.DebitCard -> PaymentMethodUiModel.DebitCard
    PaymentMethod.Cash -> PaymentMethodUiModel.Cash
}
