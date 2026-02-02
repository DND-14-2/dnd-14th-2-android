package com.smtm.pickle.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.home.component.HomeProfile
import com.smtm.pickle.presentation.home.component.HomeTopBar
import com.smtm.pickle.presentation.home.component.LedgerCalendar
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToLedgerCreate: () -> Unit,
    onNavigateToLedgerDetail: (Long) -> Unit,
) {
    HomeContent(
        monthlyTotalIncome = 1_000_000,
        monthlyTotalExpense = 500_000,
        ledgerCalendarDays = emptyMap(),
        selectedYearMonth = YearMonth.now(),
        selectedDate = LocalDate.now(),
        onDateClick = {},
        onMonthChanged = {},
        onNavigateToLedgerCreate = onNavigateToLedgerCreate,
        onNavigateToLedgerDetail = onNavigateToLedgerDetail,
    )
}

@Composable
private fun HomeContent(
    monthlyTotalIncome: Long,
    monthlyTotalExpense: Long,
    ledgerCalendarDays: Map<LocalDate, LedgerCalendarDay>,
    selectedYearMonth: YearMonth,
    selectedDate: LocalDate,
    onDateClick: (LocalDate) -> Unit,
    onMonthChanged: (YearMonth) -> Unit,
    onNavigateToLedgerCreate: () -> Unit,
    onNavigateToLedgerDetail: (Long) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = PickleTheme.colors.background50,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(PickleTheme.colors.background50),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item("top_bar") {
                HomeTopBar()
            }

            item("profile") {
                HomeProfile(
                    badge = "뱃지명",
                    nickname = "나의닉네임",
                    income = monthlyTotalIncome,
                    expense = monthlyTotalExpense,
                )
            }
            item("ledger_calendar") {
                LedgerCalendar(
                    ledgerCalendarDays = ledgerCalendarDays,
                    selectedYearMonth = selectedYearMonth,
                    selectedDate = selectedDate,
                    onDateClick = onDateClick,
                    onMonthChanged = onMonthChanged,
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeContentPreview() {
    PickleTheme {
        HomeContent(
            monthlyTotalIncome = 1000000,
            monthlyTotalExpense = 200000,
            ledgerCalendarDays = emptyMap(),
            selectedYearMonth = YearMonth.now(),
            selectedDate = LocalDate.now(),
            onDateClick = {},
            onMonthChanged = {},
            onNavigateToLedgerCreate = {},
            onNavigateToLedgerDetail = {},
        )
    }
}
