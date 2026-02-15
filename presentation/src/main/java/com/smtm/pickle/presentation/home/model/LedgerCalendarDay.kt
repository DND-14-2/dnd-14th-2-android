package com.smtm.pickle.presentation.home.model

import com.smtm.pickle.domain.model.ledger.Ledger
import com.smtm.pickle.domain.model.ledger.LedgerType
import java.time.LocalDate
import kotlin.collections.component1
import kotlin.collections.component2

data class LedgerCalendarDay(
    val date: LocalDate,
    val totalExpense: Long?,
    val totalIncome: Long?,
)

fun List<Ledger>.toLedgerCalendarDays(): Map<LocalDate, LedgerCalendarDay> =
    groupBy { it.occurredOn }
        .map { (date, ledgers) ->
            val dayTotalIncome = ledgers
                .filter { it.type == LedgerType.Income }
                .sumOf { it.amount.value }
                .takeIf { it > 0 }
            val dayTotalExpense = ledgers
                .filter { it.type == LedgerType.Expense }
                .sumOf { it.amount.value }
                .takeIf { it > 0 }
            LedgerCalendarDay(
                date = date,
                totalIncome = dayTotalIncome,
                totalExpense = dayTotalExpense,
            )
        }
        .associateBy { it.date }
