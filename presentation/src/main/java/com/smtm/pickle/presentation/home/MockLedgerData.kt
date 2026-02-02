package com.smtm.pickle.presentation.home

import com.smtm.pickle.presentation.common.model.ledger.CategoryUiModel
import com.smtm.pickle.presentation.common.model.ledger.LedgerTypeUiModel
import com.smtm.pickle.presentation.common.model.ledger.LedgerUiModel
import com.smtm.pickle.presentation.common.model.ledger.PaymentMethodUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.time.YearMonth

// TODO Delete
fun mockObserveLedgersByMonth(yearMonth: YearMonth): Flow<List<LedgerUiModel>> {
    val today = LocalDate.now()
    val ledgers = if (yearMonth == YearMonth.from(today)) {
        listOf(
            LedgerUiModel(
                id = 1,
                type = LedgerTypeUiModel.Expense,
                amount = 32_000,
                category = CategoryUiModel.Food,
                description = "점심 식사",
                occurredOn = today,
                dateText = "${today.monthValue}/${today.dayOfMonth}",
                paymentMethod = PaymentMethodUiModel.CreditCard,
                memo = null,
            ),
            LedgerUiModel(
                id = 2,
                type = LedgerTypeUiModel.Income,
                amount = 50_000,
                category = CategoryUiModel.Allowance,
                description = "용돈",
                occurredOn = today,
                dateText = "${today.monthValue}/${today.dayOfMonth}",
                paymentMethod = PaymentMethodUiModel.BankTransfer,
                memo = null,
            ),
            LedgerUiModel(
                id = 3,
                type = LedgerTypeUiModel.Expense,
                amount = 15_000,
                category = CategoryUiModel.Transport,
                description = "택시비",
                occurredOn = today.minusDays(1),
                dateText = "${today.minusDays(1).monthValue}/${today.minusDays(1).dayOfMonth}",
                paymentMethod = PaymentMethodUiModel.DebitCard,
                memo = null,
            ),
        )
    } else {
        emptyList()
    }
    return flowOf(ledgers)
}

fun mockObserveLedgersByDay(date: LocalDate): Flow<List<LedgerUiModel>> {
    val today = LocalDate.now()

    val ledgers =
        if (date == today) {
            listOf(
                LedgerUiModel(
                    id = 1,
                    type = LedgerTypeUiModel.Expense,
                    amount = 32_000,
                    category = CategoryUiModel.Food,
                    description = "점심 식사",
                    occurredOn = today,
                    dateText = "${date.monthValue}/${date.dayOfMonth}",
                    paymentMethod = PaymentMethodUiModel.CreditCard,
                    memo = null,
                ),
                LedgerUiModel(
                    id = 2,
                    type = LedgerTypeUiModel.Income,
                    amount = 50_000,
                    category = CategoryUiModel.Allowance,
                    description = "용돈",
                    occurredOn = today,
                    dateText = "${date.monthValue}/${date.dayOfMonth}",
                    paymentMethod = PaymentMethodUiModel.BankTransfer,
                    memo = null,
                ),
            )
        } else if (date == today.minusDays(1)) {
            listOf(
                LedgerUiModel(
                    id = 3,
                    type = LedgerTypeUiModel.Expense,
                    amount = 15_000,
                    category = CategoryUiModel.Transport,
                    description = "택시비",
                    occurredOn = today.minusDays(1),
                    dateText = "${today.minusDays(1).monthValue}/${today.minusDays(1).dayOfMonth}",
                    paymentMethod = PaymentMethodUiModel.DebitCard,
                    memo = null,
                ),
            )
        } else {
            emptyList()
        }
    return flowOf(ledgers)
}