package com.smtm.pickle.presentation.verdict

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.model.ledger.Ledger
import com.smtm.pickle.domain.model.ledger.LedgerCategory
import com.smtm.pickle.domain.model.ledger.LedgerId
import com.smtm.pickle.domain.model.ledger.LedgerType
import com.smtm.pickle.domain.model.ledger.Money
import com.smtm.pickle.domain.model.ledger.PaymentMethod
import com.smtm.pickle.domain.model.verdict.JurorInfo
import com.smtm.pickle.domain.model.verdict.Verdict
import com.smtm.pickle.domain.model.verdict.VerdictResult
import com.smtm.pickle.domain.model.verdict.VerdictStatus
import com.smtm.pickle.domain.usecase.nickname.ObserveNicknameUseCase
import com.smtm.pickle.presentation.common.model.ledger.toUiModel
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
    private var allMyJudgements: List<Verdict> = emptyList()
    private var allMyVerdicts: List<Verdict> = emptyList()

    init {
        loadDummyData()
    }

    fun onTabSelected(index: Int) {
        _uiState.update { it.copy(selectedTabIndex = index) }
        updateUiState()
    }

    fun onFilterSelected(index: Int) {
        _uiState.update { state ->
            if (state.selectedTabIndex == 0) {
                state.copy(
                    judgements = state.judgements.copy(filterIndex = index)
                )
            } else {
                state.copy(
                    verdicts = state.verdicts.copy(filterIndex = index)
                )
            }
        }
        updateUiState()
    }

    fun onVerdictItemClick(verdict: VerdictUiModel) {
        when (verdict.status) {
            VerdictStatus.PENDING -> {
                if (_uiState.value.selectedTabIndex == 0) {
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

    fun onSubmitJudgement(isGuilty: Boolean) {
        val verdict = _uiState.value.selectedVerdictForJudgement ?: return
        // TODO: Handle judgement submission logic here
        
        // 제출 성공 시 완료 화면 이동
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

        updateUiState()
    }

    private fun updateUiState() {
        val currentMyJudgments = allMyJudgements
        val currentMyVerdicts = allMyVerdicts

        val filteredJudgements = filterVerdicts(currentMyJudgments, _uiState.value.judgements.filterIndex)
        val filteredVerdicts = filterVerdicts(currentMyVerdicts, _uiState.value.verdicts.filterIndex)

        _uiState.update { state ->
            state.copy(
                judgements = state.judgements.copy(
                    items = filteredJudgements.map { verdict -> verdict.toUiModel() },
                    counts = calculateCounts(currentMyJudgments)
                ),
                verdicts = state.verdicts.copy(
                    items = filteredVerdicts.map { verdict -> verdict.toUiModel() },
                    counts = calculateCounts(currentMyVerdicts)
                ),
            )
        }
    }

    private fun calculateCounts(verdicts: List<Verdict>): VerdictCounts {
        return VerdictCounts(
            total = verdicts.size,
            pending = verdicts.count { it.status == VerdictStatus.PENDING },
            completed = verdicts.count { it.status == VerdictStatus.COMPLETED }
        )
    }

    private fun filterVerdicts(verdicts: List<Verdict>, filterIndex: Int): List<Verdict> {
        return when (filterIndex) {
            0 -> verdicts
            1 -> verdicts.filter { it.status == VerdictStatus.PENDING }
            2 -> verdicts.filter { it.status == VerdictStatus.COMPLETED }
            else -> verdicts
        }
    }

    private fun Verdict.toUiModel(): VerdictUiModel {
        return VerdictUiModel(
            id = id,
            ledger = ledger.toUiModel(),
            defendant = juror,
            status = status,
            result = result,
            createdAt = createdAt
        )
    }

    private fun createDummyMyJudgements(): List<Verdict> {
        return listOf(
            Verdict(
                id = 1,
                ledger = Ledger(
                    id = LedgerId(101),
                    type = LedgerType.Expense,
                    amount = Money(15000),
                    category = LedgerCategory.Food,
                    description = "가계부 15자 입력",
                    occurredOn = LocalDate.now(),
                    paymentMethod = PaymentMethod.BankTransfer
                ),
                juror = JurorInfo(201, "홍길동", "BADGE_1"),
                status = VerdictStatus.PENDING,
                createdAt = LocalDateTime.now().minusDays(1)
            ),
            Verdict(
                id = 2,
                ledger = Ledger(
                    id = LedgerId(102),
                    type = LedgerType.Expense,
                    amount = Money(5000),
                    category = LedgerCategory.Food,
                    description = "커피 한잔",
                    occurredOn = LocalDate.now(),
                    paymentMethod = PaymentMethod.CreditCard
                ),
                juror = JurorInfo(202, "김철수", "BADGE_2"),
                status = VerdictStatus.COMPLETED,
                result = VerdictResult.GUILTY,
                createdAt = LocalDateTime.now().minusDays(2)
            ),
            Verdict(
                id = 3,
                ledger = Ledger(
                    id = LedgerId(103),
                    type = LedgerType.Expense,
                    amount = Money(25000),
                    category = LedgerCategory.Food,
                    description = "야식 치킨",
                    occurredOn = LocalDate.now(),
                    paymentMethod = PaymentMethod.CreditCard
                ),
                juror = JurorInfo(203, "이영희", "BADGE_3"),
                status = VerdictStatus.COMPLETED,
                result = VerdictResult.INNOCENT,
                createdAt = LocalDateTime.now().minusDays(3)
            )
        )
    }

    private fun createDummyMyVerdicts(): List<Verdict> {
        return listOf(
            Verdict(
                id = 11,
                ledger = Ledger(
                    id = LedgerId(111),
                    type = LedgerType.Expense,
                    amount = Money(12000),
                    category = LedgerCategory.Transport,
                    description = "택시비",
                    occurredOn = LocalDate.now(),
                    paymentMethod = PaymentMethod.CreditCard
                ),
                juror = JurorInfo(211, "박민수", "BADGE_4"),
                status = VerdictStatus.PENDING,
                createdAt = LocalDateTime.now().minusDays(1)
            ),
            Verdict(
                id = 12,
                ledger = Ledger(
                    id = LedgerId(112),
                    type = LedgerType.Expense,
                    amount = Money(14000),
                    category = LedgerCategory.LeisureHobby,
                    description = "영화 관람",
                    occurredOn = LocalDate.now(),
                    paymentMethod = PaymentMethod.Cash
                ),
                juror = JurorInfo(212, "최수진", "BADGE_5"),
                status = VerdictStatus.COMPLETED,
                result = VerdictResult.GUILTY,
                createdAt = LocalDateTime.now().minusDays(5)
            )
        )
    }
}

data class VerdictUiState(
    val selectedTabIndex: Int = 0,
    val userNickname: String = "유저 닉네임",
    val selectedVerdict: VerdictUiModel? = null,
    val selectedVerdictForJudgement: VerdictUiModel? = null,
    val judgements: Judgements = Judgements(),
    val verdicts: Verdict = Verdict(),
) {
    data class Judgements(
        val filterIndex: Int = 0,
        val items: List<VerdictUiModel> = emptyList(),
        val counts: VerdictCounts = VerdictCounts(),
    )

    data class Verdict(
        val filterIndex: Int = 0,
        val items: List<VerdictUiModel> = emptyList(),
        val counts: VerdictCounts = VerdictCounts(),
    )
}

sealed interface VerdictEffect {
    data object NavigateToRequest : VerdictEffect
    data object NavigateToJurorList : VerdictEffect
    data class NavigateToResult(val id: Long) : VerdictEffect
    data class NavigateToJurorDetail(val id: Long) : VerdictEffect
    data class NavigateToCompleted(val defendantNickname: String) : VerdictEffect
}
