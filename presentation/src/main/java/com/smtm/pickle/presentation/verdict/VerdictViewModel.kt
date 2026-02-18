package com.smtm.pickle.presentation.verdict

import androidx.lifecycle.ViewModel
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
import com.smtm.pickle.presentation.common.model.ledger.toUiModel
import com.smtm.pickle.presentation.verdict.model.VerdictCounts
import com.smtm.pickle.presentation.verdict.model.VerdictUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class VerdictViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(VerdictUiState())
    val uiState: StateFlow<VerdictUiState> = _uiState.asStateFlow()

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
        _uiState.update {
            if (it.selectedTabIndex == 0) {
                it.copy(myJudgementFilterIndex = index)
            } else {
                it.copy(myVerdictFilterIndex = index)
            }
        }
        updateUiState()
    }

    private fun loadDummyData() {
        allMyJudgements = createDummyMyJudgements()
        allMyVerdicts = createDummyMyVerdicts()

        updateUiState()
    }

    private fun updateUiState() {
        val currentMyJudgments = allMyJudgements
        val currentMyVerdicts = allMyVerdicts

        val filteredJudgements = filterVerdicts(currentMyJudgments, _uiState.value.myJudgementFilterIndex)
        val filteredVerdicts = filterVerdicts(currentMyVerdicts, _uiState.value.myVerdictFilterIndex)

        _uiState.update {
            it.copy(
                myJudgementItems = filteredJudgements.map { verdict -> verdict.toUiModel() },
                myVerdictItems = filteredVerdicts.map { verdict -> verdict.toUiModel() },
                myJudgementCounts = calculateCounts(currentMyJudgments),
                myVerdictCounts = calculateCounts(currentMyVerdicts)
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
            juror = juror,
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
                juror = JurorInfo(201, "홍길동", "BADGE_1", "배지", "JUROR_CODE_1"),
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
                juror = JurorInfo(202, "김철수", "BADGE_2", "배지", "JUROR_CODE_2"),
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
                juror = JurorInfo(203, "이영희", "BADGE_3", "배지", "JUROR_CODE_3"),
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
                juror = JurorInfo(211, "박민수", "BADGE_4", "배지", "JUROR_CODE_4"),
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
                juror = JurorInfo(212, "최수진", "BADGE_5", "배지", "JUROR_CODE_5"),
                status = VerdictStatus.COMPLETED,
                result = VerdictResult.GUILTY,
                createdAt = LocalDateTime.now().minusDays(5)
            )
        )
    }
}

data class VerdictUiState(
    val selectedTabIndex: Int = 0,
    val myJudgementFilterIndex: Int = 0,
    val myVerdictFilterIndex: Int = 0,
    val myJudgementItems: List<VerdictUiModel> = emptyList(),
    val myVerdictItems: List<VerdictUiModel> = emptyList(),
    val myJudgementCounts: VerdictCounts = VerdictCounts(),
    val myVerdictCounts: VerdictCounts = VerdictCounts(),
)

sealed interface VerdictEffect {
    data object NavigateToCreate : VerdictEffect
    data object NavigateToRequest : VerdictEffect
    data object NavigateToResult : VerdictEffect
}
