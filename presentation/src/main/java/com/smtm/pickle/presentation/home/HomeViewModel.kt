package com.smtm.pickle.presentation.home

import androidx.lifecycle.ViewModel
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
                )
            )
        }
    }
}

data class HomeUiState(
    val profile: ProfileUiState = ProfileUiState(),
    val calendar: CalendarUiState = CalendarUiState(),
) {
    data class ProfileUiState(
        val nickname: String = "익명 닉네임",
        val badge: String = "뱃지명",
        val monthlyTotalIncome: Long = 10_000_000,
        val monthlyTotalExpense: Long = 5_000_000,
    )

    data class CalendarUiState(
        val ledgerCalendarDays: Map<LocalDate, LedgerCalendarDay> = emptyMap(),
        val selectedYearMonth: YearMonth = YearMonth.now(),
        val selectedDate: LocalDate = LocalDate.now(),
    )
}