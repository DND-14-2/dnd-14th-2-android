package com.smtm.pickle.presentation.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.model.ledger.Ledger
import com.smtm.pickle.domain.model.ledger.LedgerType
import com.smtm.pickle.domain.model.ledger.summarize
import com.smtm.pickle.domain.usecase.ledger.EnsureLedgersSyncedUseCase
import com.smtm.pickle.domain.usecase.ledger.ObserveLedgersByMonthUseCase
import com.smtm.pickle.domain.usecase.nickname.ObserveNicknameUseCase
import com.smtm.pickle.presentation.common.model.ledger.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getNicknameUseCase: ObserveNicknameUseCase,
    private val observeLedgersByMonthUseCase: ObserveLedgersByMonthUseCase,
    private val ensureLedgersSyncedUseCase: EnsureLedgersSyncedUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyPageUiState())
    val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

    private val _effect : Channel<MyPageEffect> = Channel(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        observeNickname()
        ensureLedgerSynced()
        observeStatistics()
    }

    private fun observeNickname() {
        viewModelScope.launch {
            getNicknameUseCase().collect { nickname ->
                _uiState.update { state ->
                    state.copy(
                        profile = state.profile.copy(nickname = nickname)
                    )
                }
            }
        }
    }

    private fun observeStatistics() {
        viewModelScope.launch {
            val now = YearMonth.now()
            observeLedgersByMonthUseCase(
                yearMonth = now,
                backwardMonths = 1,
                forwardMonths = 0,
            ).collect { ledgers ->
                val byMonth = ledgers.groupBy { YearMonth.from(it.occurredOn) }
                val thisMonth = byMonth[now].orEmpty()
                val lastMonth = byMonth[now.minusMonths(1)].orEmpty()

                val thisMonthSummary = thisMonth.summarize()
                val lastMonthSummary = lastMonth.summarize()

                _uiState.update { state ->
                    state.copy(
                        statistics = MyPageUiState.StatisticsState(
                            selectedTabIndex = state.statistics.selectedTabIndex,
                            expenditure = MyPageUiState.StatisticsDetailState(
                                totalAmount = thisMonthSummary.totalExpense,
                                comparedToPreviousMonth = lastMonthSummary.totalExpense - thisMonthSummary.totalExpense,
                                month = now.monthValue,
                                chartItems = thisMonth.toChartItems(LedgerType.Expense),
                            ),
                            income = MyPageUiState.StatisticsDetailState(
                                totalAmount = thisMonthSummary.totalIncome,
                                comparedToPreviousMonth = thisMonthSummary.totalIncome - lastMonthSummary.totalIncome,
                                month = now.monthValue,
                                chartItems = thisMonth.toChartItems(LedgerType.Income),
                            ),
                        )
                    )
                }
            }
        }
    }

    private fun ensureLedgerSynced() {
        viewModelScope.launch {
            ensureLedgersSyncedUseCase(
                baseMonth = YearMonth.now(),
                monthsBack = 1,
                monthsForward = 0,
            ).onFailure { e ->
                Timber.e(e, "ensureLedgersSynced() failed")
                _effect.send(MyPageEffect.ShowSnackBar("최신 데이터를 불러오는데 실패했습니다."))
            }
        }
    }

    fun onStatisticsTabSelected(index: Int) {
        _uiState.update { state ->
            state.copy(
                statistics = state.statistics.copy(selectedTabIndex = index)
            )
        }
    }

    private fun List<Ledger>.toChartItems(type: LedgerType): List<MyPageUiState.ChartItemState> =
        filter { it.type == type }
            .groupBy { it.category }
            .map { (category, items) ->
                val uiModel = category.toUiModel()
                MyPageUiState.ChartItemState(
                    labelResId = uiModel.stringResId,
                    value = items.sumOf { it.amount.value }.toFloat(),
                    colorHex = uiModel.chartColorHex,
                )
            }
            .sortedByDescending { it.value }
}

sealed interface MyPageEffect {
    data class ShowSnackBar(val msg: String) : MyPageEffect
}
