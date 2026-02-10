package com.smtm.pickle.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.model.ledger.summarize
import com.smtm.pickle.domain.usecase.ledger.EnsureLedgersSyncedUseCase
import com.smtm.pickle.domain.usecase.ledger.ObserveLedgersByDayUseCase
import com.smtm.pickle.domain.usecase.ledger.ObserveLedgersByMonthUseCase
import com.smtm.pickle.domain.usecase.nickname.ObserveNicknameUseCase
import com.smtm.pickle.presentation.common.model.ledger.LedgerUiModel
import com.smtm.pickle.presentation.common.model.ledger.toUiModel
import com.smtm.pickle.presentation.home.model.LedgerCalendarDay
import com.smtm.pickle.presentation.home.model.toLedgerCalendarDays
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import timber.log.Timber
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeLedgersByMonthUseCase: ObserveLedgersByMonthUseCase,
    private val observeLedgersByDayUseCase: ObserveLedgersByDayUseCase,
    private val ensureLedgersSyncedUseCase: EnsureLedgersSyncedUseCase,
    private val observeNicknameUseCase: ObserveNicknameUseCase,
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

    private val _effect: MutableSharedFlow<HomeEffect> = MutableSharedFlow(replay = 0)
    val effect: SharedFlow<HomeEffect> = _effect.asSharedFlow()

    init {
        observeNickname()
        observeMonthLedgers()
        observeSelectedDateLedgers()
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
                ensureLedgersSynced(yearMonth)
                observeLedgersByMonthUseCase(yearMonth)
                    .catch { e ->
                        Timber.e(e, "observeLedgersByMonthUseCase() failed")
                        _effect.emit(HomeEffect.ShowSnackBar("데이터를 불러오는데 실패했습니다."))
                    }
            }.onEach { ledgers ->
                val summary = ledgers.summarize()
                val ledgerCalendarDays = ledgers.toLedgerCalendarDays()

                _uiState.update { state ->
                    state.copy(
                        calendar = state.calendar.copy(
                            ledgerCalendarDays = ledgerCalendarDays,
                        ),
                        profile = state.profile.copy(
                            monthlyTotalIncome = summary.totalIncome,
                            monthlyTotalExpense = summary.totalExpense
                        ),
                    )
                }
            }.launchIn(viewModelScope)
    }

    private suspend fun ensureLedgersSynced(yearMonth: YearMonth) {
        ensureLedgersSyncedUseCase(
            baseMonth = yearMonth,
            monthsBack = 3,
            monthsForward = 3,
        ).onFailure { e ->
            Timber.e(e, "ensureLedgersSynced() failed")
            _effect.emit(HomeEffect.ShowSnackBar("최신 데이터를 불러오는데 실패했습니다."))
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeSelectedDateLedgers() {
        selectedDate
            .flatMapLatest { date ->
                observeLedgersByDayUseCase(date)
                    .catch { e ->
                        Timber.e(e, "observeLedgersByDayUseCase() failed")
                        _effect.emit(HomeEffect.ShowSnackBar("데이터를 불러오는데 실패했습니다."))
                    }
            }.onEach { ledgers ->
                val summary = ledgers.summarize()

                _uiState.update { state ->
                    state.copy(
                        dailyLedger = state.dailyLedger.copy(
                            ledgers = ledgers.map { it.toUiModel() },
                            totalIncome = summary.totalIncome,
                            totalExpense = summary.totalExpense,
                        ),
                    )
                }
            }.launchIn(viewModelScope)
    }

    private fun observeNickname() {
        observeNicknameUseCase()
            .onEach { nickname ->
                _uiState.update { state ->
                    state.copy(
                        profile = state.profile.copy(
                            nickname = nickname
                        )
                    )
                }
            }
            .catch {
                Timber.e(it, "observeNicknameUseCase() failed")
            }
            .launchIn(viewModelScope)
    }
}

data class HomeUiState(
    val profile: ProfileState = ProfileState(),
    val calendar: CalendarState = CalendarState(),
    val dailyLedger: DailyLedgerState = DailyLedgerState(),
) {
    data class ProfileState(
        val nickname: String = "사용자",
        val badge: String = "뱃지명",
        val monthlyTotalIncome: Long = 10_000_000,
        val monthlyTotalExpense: Long = 5_000_000,
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

sealed interface HomeEffect {
    data class ShowSnackBar(val msg: String) : HomeEffect
}
