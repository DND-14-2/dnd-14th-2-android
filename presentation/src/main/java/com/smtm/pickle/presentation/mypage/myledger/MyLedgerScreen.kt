package com.smtm.pickle.presentation.mypage.myledger

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.smtm.pickle.domain.model.ledger.LedgerId
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.appbar.PickleAppBar
import com.smtm.pickle.presentation.designsystem.components.appbar.model.NavigationItem
import com.smtm.pickle.presentation.designsystem.components.snackbar.PickleSnackbar
import com.smtm.pickle.presentation.designsystem.components.snackbar.SnackbarHost
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.home.component.dailyLedgerInfoSection
import com.smtm.pickle.presentation.mypage.myledger.component.MyLedgerCalendar
import com.smtm.pickle.presentation.mypage.myledger.component.MyLedgerMonthInfo
import java.time.LocalDate

@Composable
fun MyLedgerScreen(
    viewModel: MyLedgerViewModel = hiltViewModel(),
    onNavigateToLedgerDetail: (LedgerId) -> Unit,
    onNavigateBack: () -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarState = remember { SnackbarState() }

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is MyLedgerEffect.ShowSnackBar -> {
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

    MyLedgerContent(
        uiState = uiState,
        onDateClick = viewModel::onSelectDate,
        onLedgerClick = onNavigateToLedgerDetail,
        onNavigateBack = onNavigateBack
    )

    SnackbarHost(snackbarState = snackbarState)
}

@Composable
private fun MyLedgerContent(
    uiState: MyLedgerUiState,
    onDateClick: (LocalDate) -> Unit,
    onLedgerClick: (LedgerId) -> Unit,
    onNavigateBack: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(PickleTheme.colors.base0)
            .systemBarsPadding(),
    ) {
        CompositionLocalProvider(
            LocalOverscrollFactory provides null
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PickleTheme.colors.background50),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                stickyHeader("top_bar") {
                    PickleAppBar(
                        title = stringResource(R.string.my_ledger_title),
                        navigationItem = NavigationItem.Back(onNavigateBack),
                    )
                }

                item("month_info") {
                    MyLedgerMonthInfo(
                        month = uiState.calendar.yearMonth.monthValue,
                        totalIncome = uiState.summary.totalIncome,
                        totalExpense = uiState.summary.totalExpense,
                    )
                }

                item("ledger_calendar") {
                    MyLedgerCalendar(
                        ledgerCalendarDays = uiState.calendar.ledgerCalendarDays,
                        yearMonth = uiState.calendar.yearMonth,
                        selectedDate = uiState.calendar.selectedDate,
                        onDateClick = onDateClick,
                    )
                }

                dailyLedgerInfoSection(
                    date = uiState.dailyLedger.date,
                    ledgers = uiState.dailyLedger.ledgers,
                    totalIncome = uiState.dailyLedger.totalIncome,
                    totalExpense = uiState.dailyLedger.totalExpense,
                    onLedgerClick = onLedgerClick,
                )
            }
        }
    }
}

@Preview
@Composable
private fun MyLedgerContentPreview() {
    PickleTheme {
        MyLedgerContent(
            uiState = MyLedgerUiState(),
            onDateClick = {},
            onLedgerClick = {},
            onNavigateBack = {},
        )
    }
}
