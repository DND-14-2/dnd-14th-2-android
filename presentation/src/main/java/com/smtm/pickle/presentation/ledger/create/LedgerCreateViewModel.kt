package com.smtm.pickle.presentation.ledger.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.usecase.ledger.CreateLedgerUseCase
import com.smtm.pickle.presentation.common.model.ledger.CategoryUiModel
import com.smtm.pickle.presentation.common.model.ledger.LedgerTypeUiModel
import com.smtm.pickle.presentation.common.model.ledger.PaymentMethodUiModel
import com.smtm.pickle.presentation.common.model.ledger.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class LedgerCreateViewModel @Inject constructor(
    private val createLedgerUseCase: CreateLedgerUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LedgerCreateUiState())
    val uiState: StateFlow<LedgerCreateUiState> = _uiState.asStateFlow()

    private val _effect: Channel<LedgerCreateEffect> = Channel<LedgerCreateEffect>(Channel.BUFFERED)
    val effect: Flow<LedgerCreateEffect> = _effect.receiveAsFlow()

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

    fun createLedger(date: LocalDate, defaultDescription: String?) {
        val firstStepState = _uiState.value.firstStepState
        val amount = firstStepState.amount.toLongOrNull()
        val category = firstStepState.selectedCategory?.toDomain()
        val secondStepState = _uiState.value.secondStepState
        val paymentMethod = secondStepState.selectedPaymentMethod?.toDomain()

        if (amount == null || category == null || paymentMethod == null || defaultDescription == null) {
            Timber.e("createLedger() called with invalid state: amount=$amount, category=$category, paymentMethod=$paymentMethod")
            viewModelScope.launch {
                _effect.send(LedgerCreateEffect.ShowSnackBar("입력한 정보를 확인해주세요"))
            }
            return
        }

        val description = firstStepState.description.ifBlank { defaultDescription }
        val type = firstStepState.selectedLedgerType.toDomain()
        val memo = secondStepState.memo.ifBlank { null }

        viewModelScope.launch {
            createLedgerUseCase(
                amount = amount,
                type = type,
                category = category,
                description = description,
                occurredOn = date,
                paymentMethod = paymentMethod,
                memo = memo,
            ).onSuccess {
                _effect.send(LedgerCreateEffect.NavigateToHome)
            }.onFailure { e ->
                Timber.e(e, "createLedger() failed")
                _effect.send(LedgerCreateEffect.ShowSnackBar("네트워크 상태를 확인해주세요"))
            }
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
            _effect.send(LedgerCreateEffect.NavigateBack)
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
    data class ShowSnackBar(val msg: String) : LedgerCreateEffect
}

enum class LedgerCreateStep { First, Second }
