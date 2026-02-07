package com.smtm.pickle.presentation.ledger.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.smtm.pickle.domain.model.ledger.LedgerId
import com.smtm.pickle.presentation.common.model.ledger.LedgerUiModel
import com.smtm.pickle.presentation.navigation.route.LedgerDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LedgerDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val route = savedStateHandle.toRoute<LedgerDetailRoute>()
    private val ledgerId = LedgerId(route.ledgerId)

    private val _uiState: MutableStateFlow<LedgerDetailUiState> = MutableStateFlow(LedgerDetailUiState())
    val uiState: StateFlow<LedgerDetailUiState> = _uiState.asStateFlow()

}

data class LedgerDetailUiState(
    val ledger: LedgerUiModel? = null
)