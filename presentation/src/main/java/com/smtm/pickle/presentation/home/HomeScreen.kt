package com.smtm.pickle.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.home.component.HomeProfile
import com.smtm.pickle.presentation.home.component.HomeTopBanner
import com.smtm.pickle.presentation.home.component.HomeTopBar
import com.smtm.pickle.presentation.home.component.LedgerCalendar
import com.smtm.pickle.presentation.home.component.dailyLedgerInfoSection
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToLedgerCreate: () -> Unit,
    onNavigateToLedgerDetail: (Long) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeContent(
        profileState = uiState.profile,
        bannerState = uiState.banner,
        calendarState = uiState.calendar,
        dailyLedgerState = uiState.dailyLedger,
        onBannerClick = viewModel::onBannerClick,
        onBannerCloseClick = viewModel::onBannerCloseClick,
        onMonthChanged = viewModel::onMonthChange,
        onDateClick = viewModel::onSelectDate,
        onNavigateToLedgerCreate = onNavigateToLedgerCreate,
        onNavigateToLedgerDetail = onNavigateToLedgerDetail,
    )
}

@Composable
private fun HomeContent(
    profileState: HomeUiState.ProfileState,
    bannerState: HomeUiState.BannerState,
    calendarState: HomeUiState.CalendarState,
    dailyLedgerState: HomeUiState.DailyLedgerState,
    onBannerClick: () -> Unit,
    onBannerCloseClick: () -> Unit,
    onMonthChanged: (YearMonth) -> Unit,
    onDateClick: (LocalDate) -> Unit,
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

            if (bannerState.isVisible) {
                item("banner") {
                    HomeTopBanner(
                        onClick = onBannerClick,
                        onCloseClick = onBannerCloseClick,
                    )
                }
            }

            item("profile") {
                HomeProfile(
                    nickname = profileState.nickname,
                    badge = profileState.badge,
                    income = profileState.monthlyTotalIncome,
                    expense = profileState.monthlyTotalExpense,
                )
            }

            item("ledger_calendar") {
                LedgerCalendar(
                    ledgerCalendarDays = calendarState.ledgerCalendarDays,
                    selectedYearMonth = calendarState.selectedYearMonth,
                    selectedDate = calendarState.selectedDate,
                    onDateClick = onDateClick,
                    onMonthChanged = onMonthChanged,
                )
            }

            dailyLedgerInfoSection(
                date = dailyLedgerState.date,
                ledgers = dailyLedgerState.ledgers,
                totalIncome = dailyLedgerState.totalIncome,
                totalExpense = dailyLedgerState.totalExpense,
            )
        }
    }
}

@Preview
@Composable
private fun HomeContentPreview() {
    PickleTheme {
        HomeContent(
            profileState = HomeUiState.ProfileState(),
            bannerState = HomeUiState.BannerState(),
            calendarState = HomeUiState.CalendarState(),
            dailyLedgerState = HomeUiState.DailyLedgerState(),
            onBannerClick = {},
            onBannerCloseClick = {},
            onDateClick = {},
            onMonthChanged = {},
            onNavigateToLedgerCreate = {},
            onNavigateToLedgerDetail = {},
        )
    }
}
