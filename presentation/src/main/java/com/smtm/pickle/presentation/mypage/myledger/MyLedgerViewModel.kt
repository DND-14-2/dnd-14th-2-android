package com.smtm.pickle.presentation.mypage.myledger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.model.ledger.summarize
import com.smtm.pickle.domain.usecase.ledger.EnsureLedgersSyncedUseCase
import com.smtm.pickle.domain.usecase.ledger.ObserveLedgersByDayUseCase
import com.smtm.pickle.domain.usecase.ledger.ObserveLedgersByMonthUseCase
import com.smtm.pickle.presentation.common.model.ledger.toUiModel
import com.smtm.pickle.presentation.home.model.toLedgerCalendarDays
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class MyLedgerViewModel @Inject constructor(
    private val observeLedgersByMonthUseCase: ObserveLedgersByMonthUseCase,
    private val observeLedgersByDayUseCase: ObserveLedgersByDayUseCase,
    private val ensureLedgersSyncedUseCase: EnsureLedgersSyncedUseCase,
) : ViewModel() {

    private val selectedDate = MutableStateFlow(LocalDate.now())

    private val _uiState = MutableStateFlow(MyLedgerUiState())
    val uiState: StateFlow<MyLedgerUiState> = combine(
        _uiState,
        selectedDate,
    ) { uiState, selectedDate ->
        uiState.copy(
            calendar = uiState.calendar.copy(selectedDate = selectedDate),
            dailyLedger = uiState.dailyLedger.copy(date = selectedDate),
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MyLedgerUiState(),
    )

    private val _effect: Channel<MyLedgerEffect> = Channel(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        ensureLedgerSynced()
        observeMonthLedgers()
        observeSelectedDateLedgers()
    }

    fun onSelectDate(date: LocalDate) {
        selectedDate.value = date
    }

    private fun observeMonthLedgers() {
        val now = YearMonth.now()
        observeLedgersByMonthUseCase(
            yearMonth = now,
            backwardMonths = 0,
            forwardMonths = 0,
        ).onEach { ledgers ->
            val summary = ledgers.summarize()
            val ledgerCalendarDays = ledgers.toLedgerCalendarDays()
            _uiState.update { state ->
                state.copy(
                    summary = state.summary.copy(
                        totalIncome = summary.totalIncome,
                        totalExpense = summary.totalExpense,
                    ),
                    calendar = state.calendar.copy(
                        yearMonth = now,
                        ledgerCalendarDays = ledgerCalendarDays,
                    )
                )
            }
        }.catch { e ->
            Timber.e(e, "observeMonthLedgers() failed")
            _effect.send(MyLedgerEffect.ShowSnackBar("데이터를 불러오는데 실패했습니다."))
        }.launchIn(viewModelScope)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeSelectedDateLedgers() {
        selectedDate
            .flatMapLatest { date ->
                observeLedgersByDayUseCase(date)
                    .catch { e ->
                        Timber.e(e, "observeLedgersByDayUseCase() failed")
                        _effect.send(MyLedgerEffect.ShowSnackBar("데이터를 불러오는데 실패했습니다."))
                    }
            }.onEach { ledgers ->
                val summary = ledgers.summarize()
                _uiState.update { state ->
                    state.copy(
                        dailyLedger = state.dailyLedger.copy(
                            ledgers = ledgers.map { it.toUiModel() },
                            totalIncome = summary.totalIncome,
                            totalExpense = summary.totalExpense,
                        )
                    )
                }
            }.catch { e ->
                Timber.e(e, "observeSelectedDateLedgers() failed")
                _effect.send(MyLedgerEffect.ShowSnackBar("데이터를 불러오는데 실패했습니다."))
            }.launchIn(viewModelScope)
    }

    private fun ensureLedgerSynced() {
        viewModelScope.launch {
            ensureLedgersSyncedUseCase(
                baseMonth = YearMonth.now(),
                monthsBack = 0,
                monthsForward = 0,
            ).onFailure { e ->
                Timber.e(e, "ensureLedgerSynced() failed")
                _effect.send(MyLedgerEffect.ShowSnackBar("최신 데이터를 불러오는데 실패했습니다."))
            }
        }
    }
}
