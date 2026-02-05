package com.smtm.pickle.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.presentation.common.model.ledger.LedgerTypeUiModel
import com.smtm.pickle.presentation.common.model.ledger.LedgerUiModel
import com.smtm.pickle.presentation.home.model.LedgerCalendarDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {

    private val selectedYearMonth = MutableStateFlow(YearMonth.now())
    private val selectedDate = MutableStateFlow(LocalDate.now())
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = combine(
        _uiState,
        selectedYearMonth,
        selectedDate,
    ) { uiState, selectedYearMonth, selectedDate ->
        uiState.copy(
            calendar = uiState.calendar.copy(
                selectedYearMonth = selectedYearMonth,
                selectedDate = selectedDate,
            ),
            dailyLedger = uiState.dailyLedger.copy(
                date = selectedDate,
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState()
    )

    init {
        observeMonthLedgers()
        observeSelectedDateLedgers()
    }

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
        selectedYearMonth.value = yearMonth
    }

    fun onSelectDate(date: LocalDate) {
        selectedDate.value = date
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeMonthLedgers() {
        selectedYearMonth
            .flatMapLatest { yearMonth ->
                mockObserveLedgersByMonth(yearMonth)
            }.onEach { ledgers ->
                val calendarDays = ledgers.groupBy { it.occurredOn }
                    .mapValues { (date, items) ->
                        LedgerCalendarDay(
                            date = date,
                            totalIncome = items.filter { it.type is LedgerTypeUiModel.Income }
                                .sumOf { it.amount }.takeIf { it > 0 },
                            totalExpense = items.filter { it.type is LedgerTypeUiModel.Expense }
                                .sumOf { it.amount }.takeIf { it > 0 },
                        )
                    }
                _uiState.update { state ->
                    state.copy(
                        calendar = state.calendar.copy(
                            ledgerCalendarDays = calendarDays,
                        ),
                        profile = state.profile.copy(
                            monthlyTotalIncome = ledgers
                                .filter { it.type is LedgerTypeUiModel.Income }
                                .sumOf { it.amount },
                            monthlyTotalExpense = ledgers
                                .filter { it.type is LedgerTypeUiModel.Expense }
                                .sumOf { it.amount },
                        ),
                    )
                }
            }.launchIn(viewModelScope)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeSelectedDateLedgers() {
        selectedDate
            .flatMapLatest { date ->
                mockObserveLedgersByDay(date)
            }.onEach { ledgers ->
                _uiState.update { state ->
                    state.copy(
                        dailyLedger = state.dailyLedger.copy(
                            ledgers = ledgers,
                            totalIncome = ledgers
                                .filter { it.type is LedgerTypeUiModel.Income }
                                .sumOf { it.amount },
                            totalExpense = ledgers
                                .filter { it.type is LedgerTypeUiModel.Expense }
                                .sumOf { it.amount },
                        ),
                    )
                }
            }.launchIn(viewModelScope)
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