package com.smtm.pickle.presentation.ledger.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smtm.pickle.domain.model.ledger.LedgerCategory
import com.smtm.pickle.domain.model.ledger.LedgerId
import com.smtm.pickle.domain.usecase.ledger.EditLedgerUseCase
import com.smtm.pickle.domain.usecase.ledger.GetLedgerUseCase
import com.smtm.pickle.presentation.common.model.ledger.CategoryUiModel
import com.smtm.pickle.presentation.common.model.ledger.LedgerTypeUiModel
import com.smtm.pickle.presentation.common.model.ledger.PaymentMethodUiModel
import com.smtm.pickle.presentation.common.model.ledger.toDomain
import com.smtm.pickle.presentation.common.model.ledger.toUiModel
import com.smtm.pickle.presentation.navigation.route.LedgerEditRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class LedgerEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getLedgerUseCase: GetLedgerUseCase,
    private val editLedgerUseCase: EditLedgerUseCase,
) : ViewModel() {

    private val ledgerId: Long = savedStateHandle.toRoute<LedgerEditRoute>().ledgerId
    private val _showExitDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showExitDialog: StateFlow<Boolean> = _showExitDialog.asStateFlow()

    private val _uiState: MutableStateFlow<LedgerUpdateUiState> = MutableStateFlow(LedgerUpdateUiState())
    val uiState: StateFlow<LedgerUpdateUiState> = _uiState.asStateFlow()

    private val _effect: MutableSharedFlow<LedgerEditEffect> = MutableSharedFlow(replay = 0)
    val effect: SharedFlow<LedgerEditEffect> = _effect.asSharedFlow()

    init {
        loadLedger()
    }

    private fun loadLedger() {
        viewModelScope.launch {
            getLedgerUseCase(LedgerId(ledgerId))
                .onSuccess { ledger ->
                    _uiState.update { state ->
                        state.copy(
                            date = ledger.occurredOn,
                            firstStepState = state.firstStepState.copy(
                                amount = ledger.amount.value.toString(),
                                selectedLedgerType = ledger.type.toUiModel(),
                                selectedCategory = ledger.category.toUiModel(),
                                description = ledger.description,
                            ),
                            secondStepState = state.secondStepState.copy(
                                selectedPaymentMethod = ledger.paymentMethod.toUiModel(),
                                memo = ledger.memo ?: "",
                            )
                        )
                    }
                }
                .onFailure { e ->
                    Timber.e(e, "loadLedger() ledger not found: ledgerId=$ledgerId")
                    _effect.emit(LedgerEditEffect.NavigateBack)
                }

        }
    }

    fun setStep(step: LedgerEditStep) {
        _uiState.update { state -> state.copy(step = step) }
    }

    fun setAmount(amount: String) {
        _uiState.update { state ->
            state.copy(
                firstStepState = state.firstStepState.copy(amount = amount)
            )
        }
    }

    fun selectLedgerType(type: LedgerTypeUiModel) {
        _uiState.update { state ->
            state.copy(
                firstStepState = state.firstStepState.copy(selectedLedgerType = type)
            )
        }
    }

    fun selectCategory(category: CategoryUiModel?) {
        _uiState.update { state ->
            state.copy(
                firstStepState = state.firstStepState.copy(selectedCategory = category)
            )
        }
    }

    fun setDescription(description: String) {
        _uiState.update { state ->
            state.copy(
                firstStepState = state.firstStepState.copy(description = description)
            )
        }
    }

    fun selectPaymentMethod(paymentMethod: PaymentMethodUiModel) {
        _uiState.update { state ->
            state.copy(
                secondStepState = state.secondStepState.copy(selectedPaymentMethod = paymentMethod)
            )
        }
    }

    fun setMemo(memo: String) {
        _uiState.update { state ->
            state.copy(
                secondStepState = state.secondStepState.copy(memo = memo)
            )
        }
    }

    fun updateLedger() {
        val firstStepState = _uiState.value.firstStepState
        val amount = firstStepState.amount.toLongOrNull()
        val category = firstStepState.selectedCategory?.toDomain()
        val secondStepState = _uiState.value.secondStepState
        val paymentMethod = secondStepState.selectedPaymentMethod?.toDomain()
        val date = _uiState.value.date

        if (amount == null || amount <= 0 || category == null || paymentMethod == null) {
            Timber.e("updateLedger() called with invalid state: amount=$amount, category=$category, paymentMethod=$paymentMethod, date=$date")
            viewModelScope.launch {
                _effect.emit(LedgerEditEffect.ShowSnackBar("입력한 정보를 확인해주세요"))
            }
            return
        }

        val description = firstStepState.description.ifEmpty { getDefaultDescription(category) }
        val type = firstStepState.selectedLedgerType.toDomain()
        val memo = secondStepState.memo.ifBlank { null }

        viewModelScope.launch {
            editLedgerUseCase(
                ledgerId = ledgerId,
                amount = amount,
                type = type,
                category = category,
                description = description,
                occurredOn = date,
                paymentMethod = paymentMethod,
                memo = memo,
            ).onSuccess {
                _effect.emit(LedgerEditEffect.NavigateBack)
            }.onFailure { e ->
                Timber.e(e, "updateLedger() failed")
                _effect.emit(LedgerEditEffect.ShowSnackBar("네트워크 상태를 확인해주세요"))
            }
        }
    }

    private fun getDefaultDescription(category: LedgerCategory): String {
        return when (category) {
            LedgerCategory.Food -> "식비"
            LedgerCategory.Transport -> "교통비"
            LedgerCategory.Housing -> "주거비"
            LedgerCategory.Shopping -> "쇼핑"
            LedgerCategory.HealthMedical -> "의료/건강"
            LedgerCategory.EducationSelfDevelopment -> "교육/자기계발"
            LedgerCategory.LeisureHobby -> "여가/취미"
            LedgerCategory.SavingFinance -> "저축/금융"
            LedgerCategory.Salary -> "월급"
            LedgerCategory.SideIncome -> "부수입"
            LedgerCategory.Bonus -> "상여"
            LedgerCategory.Allowance -> "용돈"
            LedgerCategory.PartTimeIncome -> "아르바이트"
            LedgerCategory.FinancialIncome -> "금융수입"
            LedgerCategory.SplitBill -> "더치페이"
            LedgerCategory.Transfer -> "이체"
            LedgerCategory.Other -> "기타"
        }
    }

    fun showExitDialog() {
        _showExitDialog.update { true }
    }

    fun dismissExitDialog() {
        _showExitDialog.update { false }
    }

    fun confirmExit() {
        _showExitDialog.update { false }
        viewModelScope.launch {
            _effect.emit(LedgerEditEffect.NavigateBack)
        }
    }
}

data class LedgerUpdateUiState(
    val step: LedgerEditStep = LedgerEditStep.First,
    val date: LocalDate = LocalDate.now(),
    val firstStepState: FirstStepState = FirstStepState(),
    val secondStepState: SecondStepState = SecondStepState(),
) {
    data class FirstStepState(
        val amount: String = "",
        val selectedLedgerType: LedgerTypeUiModel = LedgerTypeUiModel.Expense,
        val selectedCategory: CategoryUiModel? = null,
        val description: String = "",
    ) {
        val isNextEnabled: Boolean
            get() = amount.toLongOrNull()?.takeIf { it > 0 } != null &&
                    selectedCategory != null
    }

    data class SecondStepState(
        val selectedPaymentMethod: PaymentMethodUiModel? = null,
        val memo: String = "",
    ) {
        val isSuccessEnabled: Boolean
            get() = selectedPaymentMethod != null
    }
}

sealed interface LedgerEditEffect {
    data object NavigateBack : LedgerEditEffect
    data class ShowSnackBar(val msg: String) : LedgerEditEffect
}

enum class LedgerEditStep { First, Second }