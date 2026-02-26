package com.smtm.pickle.presentation.ledger.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smtm.pickle.domain.model.ledger.LedgerId
import com.smtm.pickle.domain.usecase.ledger.DeleteLedgerUseCase
import com.smtm.pickle.domain.usecase.ledger.ObserveLedgerUseCase
import com.smtm.pickle.domain.usecase.ledger.SyncLedgerUseCase
import com.smtm.pickle.domain.usecase.verdict.RequestVerdictUseCase
import com.smtm.pickle.presentation.common.model.ledger.LedgerUiModel
import com.smtm.pickle.presentation.common.model.ledger.toUiModel
import com.smtm.pickle.presentation.navigation.route.LedgerDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LedgerDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val observeLedgerUseCase: ObserveLedgerUseCase,
    private val syncLedgerUseCase: SyncLedgerUseCase,
    private val deleteLedgerUseCase: DeleteLedgerUseCase,
    private val requestVerdictUseCase: RequestVerdictUseCase,
) : ViewModel() {

    private val route = savedStateHandle.toRoute<LedgerDetailRoute>()
    private val ledgerId = LedgerId(route.ledgerId)

    private val _uiState: MutableStateFlow<LedgerDetailUiState> = MutableStateFlow(LedgerDetailUiState.Loading)
    val uiState: StateFlow<LedgerDetailUiState> = _uiState.asStateFlow()

    private val _effect: MutableSharedFlow<LedgerDetailEffect> = MutableSharedFlow(replay = 0)
    val effect: SharedFlow<LedgerDetailEffect> = _effect.asSharedFlow()

    private val _dialogState = MutableStateFlow<LedgerDetailDialogState>(LedgerDetailDialogState.None)
    val dialogState: StateFlow<LedgerDetailDialogState> = _dialogState.asStateFlow()

    init {
        observeLedger()
        syncLedger()
    }

    private fun observeLedger() {
        observeLedgerUseCase(ledgerId)
            .onEach { ledger ->
                _uiState.update {
                    LedgerDetailUiState.Success(ledger = ledger.toUiModel())
                }
            }
            .catch { e ->
                Timber.e(e, "Failed to observe ledger: id=${ledgerId.value}")
                _uiState.update {
                    LedgerDetailUiState.Error
                }
            }
            .launchIn(viewModelScope)
    }

    private fun syncLedger() {
        viewModelScope.launch {
            syncLedgerUseCase(ledgerId)
                .onFailure { e ->
                    Timber.e(e, "Failed to sync ledger: id=${ledgerId.value}")
                    // Room 데이터가 없는 경우에만 에러 처리
                    if (_uiState.value is LedgerDetailUiState.Loading) {
                        _uiState.update {
                            LedgerDetailUiState.Error
                        }
                    }
                }
        }
    }

    fun onDeleteClick() {
        _dialogState.update { LedgerDetailDialogState.DeleteConfirm }
    }

    fun onJudgmentRequestClick() {
        _dialogState.update { LedgerDetailDialogState.JudgmentRequest }
    }

    fun dismissDialog() {
        _dialogState.update { LedgerDetailDialogState.None }
    }

    fun deleteLedger() {
        viewModelScope.launch {
            dismissDialog()
            deleteLedgerUseCase(ledgerId)
                .onSuccess {
                    _effect.emit(LedgerDetailEffect.NavigateBack)
                }
                .onFailure { e ->
                    Timber.e(e, "Failed to delete ledger: id=${ledgerId.value}")
                    _effect.emit(LedgerDetailEffect.ShowSnackBar(e.message ?: "네트워크 상태를 확인해주세요"))
                }
        }
    }

    fun confirmJudgmentRequest() {
        viewModelScope.launch {
            dismissDialog()
            val ledgerState = _uiState.value
            if (ledgerState !is LedgerDetailUiState.Success) return@launch

            requestVerdictUseCase(ledgerState.ledger.id)
                .onSuccess {
                    _effect.emit(LedgerDetailEffect.ShowSnackBar("내 배심원에게 심판 요청을 보냈어요"))
                }
                .onFailure { e ->
                    Timber.e(e, "심판 요청 실패: ledgerId=${ledgerState.ledger.id}")
                    _effect.emit(LedgerDetailEffect.ShowSnackBar(e.message ?: "오류가 발생했어요 잠시후 다시 시도해주세요"))
                }
        }
    }

    fun navigateBack() {
        viewModelScope.launch {
            _effect.emit(LedgerDetailEffect.NavigateBack)
        }
    }

    fun navigateToUpdate() {
        viewModelScope.launch {
            _effect.emit(LedgerDetailEffect.NavigateToEdit)
        }
    }
}

sealed interface LedgerDetailDialogState {
    data object None : LedgerDetailDialogState
    data object DeleteConfirm : LedgerDetailDialogState
    data object JudgmentRequest : LedgerDetailDialogState
}

sealed interface LedgerDetailUiState {
    data object Loading : LedgerDetailUiState
    data class Success(
        val ledger: LedgerUiModel
    ) : LedgerDetailUiState

    data object Error : LedgerDetailUiState
}

sealed interface LedgerDetailEffect {
    data object NavigateBack : LedgerDetailEffect
    data object NavigateToEdit : LedgerDetailEffect
    data class ShowSnackBar(val msg: String) : LedgerDetailEffect
}
