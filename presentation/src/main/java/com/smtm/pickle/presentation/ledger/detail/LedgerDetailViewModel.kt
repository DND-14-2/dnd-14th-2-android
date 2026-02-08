package com.smtm.pickle.presentation.ledger.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smtm.pickle.domain.model.ledger.LedgerId
import com.smtm.pickle.domain.usecase.ledger.DeleteLedgerUseCase
import com.smtm.pickle.domain.usecase.ledger.GetLedgerUseCase
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LedgerDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getLedgerUseCase: GetLedgerUseCase,
    private val deleteLedgerUseCase: DeleteLedgerUseCase,
) : ViewModel() {

    private val route = savedStateHandle.toRoute<LedgerDetailRoute>()
    private val ledgerId = LedgerId(route.ledgerId)

    private val _uiState: MutableStateFlow<LedgerDetailUiState> = MutableStateFlow(LedgerDetailUiState.Loading)
    val uiState: StateFlow<LedgerDetailUiState> = _uiState.asStateFlow()

    private val _effect: MutableSharedFlow<LedgerDetailEffect> = MutableSharedFlow(replay = 0)
    val effect: SharedFlow<LedgerDetailEffect> = _effect.asSharedFlow()

    private val _showDeleteDialog = MutableStateFlow(false)
    val showDeleteDialog: StateFlow<Boolean> = _showDeleteDialog.asStateFlow()

    init {
        initializeData()
    }

    private fun initializeData() {
        viewModelScope.launch {
            getLedgerUseCase(ledgerId)
                .onSuccess { ledger ->
                    _uiState.update {
                        LedgerDetailUiState.Success(ledger = ledger.toUiModel())
                    }
                }
                .onFailure { e ->
                    Timber.e(e, "Failed to get ledger: id=${ledgerId.value}")
                    _uiState.update {
                        LedgerDetailUiState.Error
                    }
                }
        }
    }

    fun showDeleteDialog() {
        _showDeleteDialog.update { true }
    }

    fun dismissDeleteDialog() {
        _showDeleteDialog.update { false }
    }

    fun deleteLedger() {
        viewModelScope.launch {
            dismissDeleteDialog()
            deleteLedgerUseCase(ledgerId)
                .onSuccess {
                    _effect.emit(LedgerDetailEffect.NavigateToHome)
                }
                .onFailure { e ->
                    Timber.e(e, "Failed to delete ledger: id=${ledgerId.value}")
                    _effect.emit(LedgerDetailEffect.ShowSnackBar("네트워크 상태를 확인해주세요"))
                }
        }
    }

    fun navigateToHome() {
        viewModelScope.launch {
            _effect.emit(LedgerDetailEffect.NavigateToHome)
        }
    }
}

sealed interface LedgerDetailUiState {
    data object Loading : LedgerDetailUiState
    data class Success(
        val ledger: LedgerUiModel
    ) : LedgerDetailUiState

    data object Error : LedgerDetailUiState
}

sealed interface LedgerDetailEffect {
    data object NavigateToHome : LedgerDetailEffect
    data class ShowSnackBar(val msg: String) : LedgerDetailEffect
}
