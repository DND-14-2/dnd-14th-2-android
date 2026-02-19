package com.smtm.pickle.presentation.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.smtm.pickle.domain.model.ledger.LedgerId
import com.smtm.pickle.presentation.common.utils.BackPressFinishHandler
import com.smtm.pickle.presentation.designsystem.components.snackbar.PickleSnackbar
import com.smtm.pickle.presentation.designsystem.components.snackbar.SnackbarHost
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarPosition
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.home.component.HomeProfile
import com.smtm.pickle.presentation.home.component.HomeTopBar
import com.smtm.pickle.presentation.home.component.LedgerCalendar
import com.smtm.pickle.presentation.home.component.dailyLedgerInfoSection
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    isFabExpanded: Boolean,
    onFabClose: () -> Unit,
    onSelectedDateChange: (LocalDate) -> Unit,
    onNavigateToMyPage: () -> Unit,
    onNavigateToLedgerDetail: (LedgerId) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarState = remember { SnackbarState() }

    BackHandler(enabled = isFabExpanded) {
        onFabClose()
    }

    if (!isFabExpanded) {
        BackPressFinishHandler(
            snackBarState = snackbarState,
            position = SnackbarPosition.BelowStatusBar
        )
    }

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is HomeEffect.ShowSnackBar -> {
                        snackbarState.show(
                            PickleSnackbar.snackbarShort(
                                message = effect.msg,
                            )
                        )
                    }
                }
            }
        }
    }

    HomeContent(
        profileState = uiState.profile,
        calendarState = uiState.calendar,
        dailyLedgerState = uiState.dailyLedger,
        onMonthChanged = viewModel::onMonthChange,
        onDateClick = { date ->
            viewModel.onSelectDate(date)
            onSelectedDateChange(date)
        },
        onNavigateToMyPage = onNavigateToMyPage,
        onNavigateToLedgerDetail = onNavigateToLedgerDetail,
    )

    SnackbarHost(snackbarState = snackbarState)
}

@Composable
private fun HomeContent(
    profileState: HomeUiState.ProfileState,
    calendarState: HomeUiState.CalendarState,
    dailyLedgerState: HomeUiState.DailyLedgerState,
    onMonthChanged: (YearMonth) -> Unit,
    onDateClick: (LocalDate) -> Unit,
    onNavigateToMyPage: () -> Unit,
    onNavigateToLedgerDetail: (LedgerId) -> Unit,
) {
    Scaffold(
        topBar = {
            HomeTopBar(
                modifier = Modifier.statusBarsPadding(),
                onStatisticsClick = onNavigateToMyPage
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .background(PickleTheme.colors.background50),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item("profile") {
                HomeProfile(
                    nickname = profileState.nickname,
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
                onLedgerClick = onNavigateToLedgerDetail
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
            calendarState = HomeUiState.CalendarState(),
            dailyLedgerState = HomeUiState.DailyLedgerState(),
            onDateClick = {},
            onMonthChanged = {},
            onNavigateToMyPage = {},
            onNavigateToLedgerDetail = {},
        )
    }
}
