package com.smtm.pickle.presentation.mypage.myledger

import com.smtm.pickle.presentation.common.model.ledger.LedgerUiModel
import com.smtm.pickle.presentation.home.model.LedgerCalendarDay
import java.time.LocalDate
import java.time.YearMonth

data class MyLedgerUiState(
    val summary: SummaryState = SummaryState(),
    val calendar: CalendarState = CalendarState(),
    val dailyLedger: DailyLedgerState = DailyLedgerState(),
) {
    data class SummaryState(
        val totalIncome: Long = 0L,
        val totalExpense: Long = 0L,
    )

    data class CalendarState(
        val yearMonth: YearMonth = YearMonth.now(),
        val ledgerCalendarDays: Map<LocalDate, LedgerCalendarDay> = emptyMap(),
        val selectedDate: LocalDate = LocalDate.now(),
    )

    data class DailyLedgerState(
        val date: LocalDate = LocalDate.now(),
        val ledgers: List<LedgerUiModel> = emptyList(),
        val totalIncome: Long = 0L,
        val totalExpense: Long = 0L,
    )
}

sealed interface MyLedgerEffect {
    data class ShowSnackBar(val msg: String) : MyLedgerEffect
}
