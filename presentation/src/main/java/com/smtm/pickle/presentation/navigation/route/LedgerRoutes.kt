package com.smtm.pickle.presentation.navigation.route

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class LedgerCreateRoute(
    val year: Int,
    val month: Int,
    val day: Int,
) {
    companion object {
        fun from(date: LocalDate) = LedgerCreateRoute(
            year = date.year,
            month = date.monthValue,
            day = date.dayOfMonth
        )
    }

    fun toLocalDate(): LocalDate = LocalDate.of(year, month, day)
}

@Serializable
data class LedgerDetailRoute(
    val ledgerId: Long
)

@Serializable
data class LedgerEditRoute(
    val ledgerId: Long
)