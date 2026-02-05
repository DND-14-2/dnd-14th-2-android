package com.smtm.pickle.presentation.home.model

import java.time.LocalDate

data class LedgerCalendarDay(
    val date: LocalDate,
    val totalExpense: Long?,
    val totalIncome: Long?,
)