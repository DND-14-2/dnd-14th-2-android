package com.smtm.pickle.presentation.ledger.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.presentation.common.model.ledger.CategoryUiModel
import com.smtm.pickle.presentation.common.model.ledger.LedgerTypeUiModel
import com.smtm.pickle.presentation.common.model.ledger.PaymentMethodUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class LedgerCreateViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(LedgerCreateUiState())
    val uiState: StateFlow<LedgerCreateUiState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<LedgerCreateEffect>(replay = 0)
    val effect: SharedFlow<LedgerCreateEffect> = _effect.asSharedFlow()

    fun setStep(step: LedgerCreateStep) {
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

    fun createLedger(date: LocalDate) {
        viewModelScope.launch {
            _effect.emit(LedgerCreateEffect.NavigateToHome)
        }
    }

    fun showExitDialog() {
        _uiState.update { state -> state.copy(showExitDialog = true) }
    }

    fun dismissExitDialog() {
        _uiState.update { it.copy(showExitDialog = false) }
    }

    fun confirmExit() {
        _uiState.update { it.copy(showExitDialog = false) }
        viewModelScope.launch {
            _effect.emit(LedgerCreateEffect.NavigateBack)
        }
    }
}

data class LedgerCreateUiState(
    val step: LedgerCreateStep = LedgerCreateStep.First,
    val firstStepState: FirstStepState = FirstStepState(),
    val secondStepState: SecondStepState = SecondStepState(),
    val showExitDialog: Boolean = false,
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

sealed interface LedgerCreateEffect {
    data object NavigateToHome : LedgerCreateEffect
    data object NavigateBack : LedgerCreateEffect
}

enum class LedgerCreateStep { First, Second }
