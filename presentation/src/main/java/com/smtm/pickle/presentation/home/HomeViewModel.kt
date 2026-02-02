package com.smtm.pickle.presentation.home

import androidx.lifecycle.ViewModel
import com.smtm.pickle.presentation.common.model.ledger.LedgerUiModel
import com.smtm.pickle.presentation.home.model.LedgerCalendarDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onBannerClick() {

    }

    fun onBannerCloseClick() {
        _uiState.update { state ->
            state.copy(
                banner = state.banner.copy(
                    isVisible = false
                )
            )
        }
    }

    fun onMonthChange(yearMonth: YearMonth) {
        _uiState.update { state ->
            state.copy(
                calendar = state.calendar.copy(
                    selectedYearMonth = yearMonth
                )
            )
        }
    }

    fun onSelectDate(selectedDate: LocalDate) {
        _uiState.update { state ->
            state.copy(
                calendar = state.calendar.copy(
                    selectedDate = selectedDate
                ),
                dailyLedger = state.dailyLedger.copy(
                    date = selectedDate
                )
            )
        }
    }
}

data class HomeUiState(
    val profile: ProfileState = ProfileState(),
    val banner: BannerState = BannerState(),
    val calendar: CalendarState = CalendarState(),
    val dailyLedger: DailyLedgerState = DailyLedgerState(),
) {
    data class ProfileState(
        val nickname: String = "익명 닉네임",
        val badge: String = "뱃지명",
        val monthlyTotalIncome: Long = 10_000_000,
        val monthlyTotalExpense: Long = 5_000_000,
    )

    data class BannerState(
        val isVisible: Boolean = true,
    )

    data class CalendarState(
        val ledgerCalendarDays: Map<LocalDate, LedgerCalendarDay> = emptyMap(),
        val selectedYearMonth: YearMonth = YearMonth.now(),
        val selectedDate: LocalDate = LocalDate.now(),
    )

    data class DailyLedgerState(
        val date: LocalDate = LocalDate.now(),
        val ledgers: List<LedgerUiModel> = emptyList(),
        val totalIncome: Long = 0L,
        val totalExpense: Long = 0L,
    )
}