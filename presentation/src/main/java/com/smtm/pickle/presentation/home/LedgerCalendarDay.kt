package com.smtm.pickle.presentation.home

import java.time.LocalDate

data class LedgerCalendarDay(
    val date: LocalDate,
    val totalExpense: Long?,
    val totalIncome: Long?,
)