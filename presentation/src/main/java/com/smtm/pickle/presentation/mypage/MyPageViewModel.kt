package com.smtm.pickle.presentation.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.model.ledger.Ledger
import com.smtm.pickle.domain.model.ledger.LedgerType
import com.smtm.pickle.domain.model.ledger.summarize
import com.smtm.pickle.domain.usecase.ledger.ObserveLedgersByMonthUseCase
import com.smtm.pickle.domain.usecase.nickname.ObserveNicknameUseCase
import com.smtm.pickle.presentation.common.model.ledger.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getNicknameUseCase: ObserveNicknameUseCase,
    private val observeLedgersByMonthUseCase: ObserveLedgersByMonthUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyPageUiState())
    val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

    init {
        observeNickname()
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
                                comparedToPreviousMonth = thisMonthSummary.totalExpense - lastMonthSummary.totalExpense,
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