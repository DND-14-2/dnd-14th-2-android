package com.smtm.pickle.presentation.verdict

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.model.verdict.VerdictResult
import com.smtm.pickle.domain.model.verdict.VerdictStatus
import com.smtm.pickle.domain.usecase.nickname.ObserveNicknameUseCase
import com.smtm.pickle.presentation.common.model.ledger.CategoryUiModel
import com.smtm.pickle.presentation.common.model.ledger.LedgerTypeUiModel
import com.smtm.pickle.presentation.common.model.ledger.LedgerUiModel
import com.smtm.pickle.presentation.common.model.ledger.PaymentMethodUiModel
import com.smtm.pickle.presentation.verdict.model.JurorUiModel
import com.smtm.pickle.presentation.verdict.model.VerdictCounts
import com.smtm.pickle.presentation.verdict.model.VerdictUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class VerdictViewModel @Inject constructor(
    observeNicknameUseCase: ObserveNicknameUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(VerdictUiState())
    val uiState: StateFlow<VerdictUiState> = combine(
        _uiState,
        observeNicknameUseCase(),
    ) { state, nickname ->
        state.copy(userNickname = nickname ?: "유저 닉네임")
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = VerdictUiState()
    )

    private val _effect = MutableSharedFlow<VerdictEffect>(replay = 0)
    val effect: SharedFlow<VerdictEffect> = _effect.asSharedFlow()

    // 원본 데이터 저장용
    private var allMyJudgements: List<VerdictUiModel> = emptyList()
    private var allMyVerdicts: List<VerdictUiModel> = emptyList()

    init {
        loadDummyData()
    }

    fun onTabSelected(index: Int) {
        _uiState.update { state ->
            state.copy(selectedTabIndex = index).applyFilters()
        }
    }

    fun onFilterSelected(index: Int) {
        _uiState.update { state ->
            val updated = if (state.selectedTabIndex == TabIndex.JUDGEMENTS) {
                state.copy(judgements = state.judgements.copy(filterIndex = index))
            } else {
                state.copy(verdicts = state.verdicts.copy(filterIndex = index))
            }
            updated.applyFilters()
        }
    }

    private fun VerdictUiState.applyFilters(): VerdictUiState {
        return copy(
            judgements = judgements.copy(
                items = filterVerdicts(allMyJudgements, judgements.filterIndex),
                counts = calculateCounts(allMyJudgements)
            ),
            verdicts = verdicts.copy(
                items = filterVerdicts(allMyVerdicts, verdicts.filterIndex),
                counts = calculateCounts(allMyVerdicts)
            )
        )
    }

    fun onVerdictItemClick(verdict: VerdictUiModel) {
        when (verdict.status) {
            VerdictStatus.PENDING -> {
                if (_uiState.value.selectedTabIndex == TabIndex.JUDGEMENTS) {
                    _uiState.update { it.copy(selectedVerdict = verdict) }
                } else {
                    _uiState.update { it.copy(selectedVerdictForJudgement = verdict) }
                }
            }

            VerdictStatus.COMPLETED -> {
                _uiState.update { it.copy(selectedVerdict = verdict) }
            }
        }
    }

    fun onJudgementDialogDismiss() {
        _uiState.update { it.copy(selectedVerdictForJudgement = null) }
    }

    // TODO: 서버에 Guilty 여부 전달
    fun onSubmitJudgement(isGuilty: Boolean) {
        val verdict = _uiState.value.selectedVerdictForJudgement ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(selectedVerdictForJudgement = null) }
            _effect.emit(VerdictEffect.NavigateToCompleted(verdict.defendant.nickname))
        }
    }

    fun onDismissBottomSheet() {
        _uiState.update { it.copy(selectedVerdict = null) }
    }

    fun navigateToJurorList() {
        viewModelScope.launch {
            _effect.emit(VerdictEffect.NavigateToJurorList)
        }
    }

    private fun loadDummyData() {
        allMyJudgements = createDummyMyJudgements()
        allMyVerdicts = createDummyMyVerdicts()

        _uiState.update { it.applyFilters() }
    }

    private fun calculateCounts(verdicts: List<VerdictUiModel>): VerdictCounts {
        return VerdictCounts(
            total = verdicts.size,
            pending = verdicts.count { it.status == VerdictStatus.PENDING },
            completed = verdicts.count { it.status == VerdictStatus.COMPLETED }
        )
    }

    private fun filterVerdicts(verdicts: List<VerdictUiModel>, filterIndex: Int): List<VerdictUiModel> {
        return when (filterIndex) {
            0 -> verdicts
            1 -> verdicts.filter { it.status == VerdictStatus.PENDING }
            2 -> verdicts.filter { it.status == VerdictStatus.COMPLETED }
            else -> verdicts
        }
    }

    private fun createDummyMyJudgements(): List<VerdictUiModel> {
        return listOf(
            VerdictUiModel(
                id = 1,
                ledger = LedgerUiModel(
                    id = 101L,
                    type = LedgerTypeUiModel.Expense,
                    amount = 15000,
                    category = CategoryUiModel.Food,
                    description = "가계부 15자 입력",
                    occurredOn = LocalDate.now(),
                    paymentMethod = PaymentMethodUiModel.BankTransfer,
                    memo = null,
                ),
                defendant = JurorUiModel(201, "홍길동"),
                status = VerdictStatus.PENDING,
                createdAt = LocalDateTime.now().minusDays(1)
            ),
            VerdictUiModel(
                id = 2,
                ledger = LedgerUiModel(
                    id = 102L,
                    type = LedgerTypeUiModel.Expense,
                    amount = 5000,
                    category = CategoryUiModel.Food,
                    description = "커피 한잔",
                    occurredOn = LocalDate.now(),
                    paymentMethod = PaymentMethodUiModel.CreditCard,
                    memo = null
                ),
                defendant = JurorUiModel(202, "김철수"),
                status = VerdictStatus.COMPLETED,
                result = VerdictResult.GUILTY,
                createdAt = LocalDateTime.now().minusDays(2)
            ),
            VerdictUiModel(
                id = 3,
                ledger = LedgerUiModel(
                    id = 103L,
                    type = LedgerTypeUiModel.Expense,
                    amount = 25000,
                    category = CategoryUiModel.Food,
                    description = "야식 치킨",
                    occurredOn = LocalDate.now(),
                    paymentMethod = PaymentMethodUiModel.CreditCard,
                    memo = null
                ),
                defendant = JurorUiModel(203, "이영희"),
                status = VerdictStatus.COMPLETED,
                result = VerdictResult.INNOCENT,
                createdAt = LocalDateTime.now().minusDays(3)
            )
        )
    }

    private fun createDummyMyVerdicts(): List<VerdictUiModel> {
        return listOf(
            VerdictUiModel(
                id = 11,
                ledger = LedgerUiModel(
                    id = 111L,
                    type = LedgerTypeUiModel.Expense,
                    amount = 12000,
                    category = CategoryUiModel.Transport,
                    description = "택시비",
                    occurredOn = LocalDate.now(),
                    paymentMethod = PaymentMethodUiModel.CreditCard,
                    memo = null
                ),
                defendant = JurorUiModel(211, "박민수"),
                status = VerdictStatus.PENDING,
                createdAt = LocalDateTime.now().minusDays(1)
            ),
            VerdictUiModel(
                id = 12,
                ledger = LedgerUiModel(
                    id = 112,
                    type = LedgerTypeUiModel.Expense,
                    amount = 14000,
                    category = CategoryUiModel.LeisureHobby,
                    description = "영화 관람",
                    occurredOn = LocalDate.now(),
                    paymentMethod = PaymentMethodUiModel.Cash,
                    memo = null
                ),
                defendant = JurorUiModel(212, "최수진"),
                status = VerdictStatus.COMPLETED,
                result = VerdictResult.GUILTY,
                createdAt = LocalDateTime.now().minusDays(5)
            )
        )
    }
}

object TabIndex {
    const val JUDGEMENTS = 0
    const val VERDICTS = 1
}

data class VerdictUiState(
    val selectedTabIndex: Int = TabIndex.JUDGEMENTS,
    val userNickname: String = "유저 닉네임",
    val selectedVerdict: VerdictUiModel? = null,
    val selectedVerdictForJudgement: VerdictUiModel? = null,
    val judgements: VerdictListState = VerdictListState(),
    val verdicts: VerdictListState = VerdictListState(),
)

data class VerdictListState(
    val filterIndex: Int = 0,
    val items: List<VerdictUiModel> = emptyList(),
    val counts: VerdictCounts = VerdictCounts(),
)

sealed interface VerdictEffect {
    data object NavigateToRequest : VerdictEffect
    data object NavigateToJurorList : VerdictEffect
    data class NavigateToResult(val id: Long) : VerdictEffect
    data class NavigateToJurorDetail(val id: Long) : VerdictEffect
    data class NavigateToCompleted(val defendantNickname: String) : VerdictEffect
}
