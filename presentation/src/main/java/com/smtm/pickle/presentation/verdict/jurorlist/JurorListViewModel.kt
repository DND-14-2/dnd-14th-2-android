package com.smtm.pickle.presentation.verdict.jurorlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.usecase.mate.GetMatesUseCase
import com.smtm.pickle.presentation.verdict.model.MateUiModel
import com.smtm.pickle.presentation.verdict.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JurorListViewModel @Inject constructor(
    private val getMatesUseCase: GetMatesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(JurorListUiState())
    val uiState: StateFlow<JurorListUiState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<JurorListEffect>()
    val effect: SharedFlow<JurorListEffect> = _effect.asSharedFlow()

    init {
        // 더미 데이터
        _uiState.update { state ->
            state.copy(
                jurors = (1..5).map { MateUiModel(it.toLong(), "지인닉네임$it", "${it * 111}", it) },
                inviteCode = "ABCDEF"
            )
        }
        loadMates()
    }


    private fun loadMates() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            getMatesUseCase()
                .onSuccess { mates ->
                    _uiState.update { it.copy(isLoading = false, jurors = mates.map { mate -> mate.toUiModel() }) }
                }
                .onFailure {
                    _uiState.update { it.copy(isLoading = false) }
                    _effect.emit(JurorListEffect.ShowSnackBar("친구 목록을 불러오지 못했습니다."))
                }
        }
    }

    fun onInviteClick() {
        _uiState.update { it.copy(dialogState = JurorListDialogState.Invite) }
    }

    fun onJurorMoreClick(jurorId: Long) {
        _uiState.update {
            it.copy(
                bottomSheetState = JurorListBottomSheetState.JurorAction(jurorId)
            )
        }
    }

    fun onDeleteJurorClick(jurorId: Long) {
        _uiState.update {
            it.copy(
                bottomSheetState = JurorListBottomSheetState.None,
                dialogState = JurorListDialogState.DeleteConfirm(jurorId)
            )
        }
    }

    fun confirmDeleteJuror() {
        val currentState = _uiState.value.dialogState
        if (currentState is JurorListDialogState.DeleteConfirm) {
            val jurorId = currentState.jurorId
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, dialogState = JurorListDialogState.None) }
                // TODO: juror 삭제 usecase

                // mock
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        jurors = state.jurors.filter { it.id != jurorId }
                    )
                }
                _effect.emit(JurorListEffect.ShowSnackBar("친구가 삭제되었습니다."))
            }
        }
    }

    fun dismissDialog() {
        _uiState.update { it.copy(dialogState = JurorListDialogState.None) }
    }

    fun dismissBottomSheet() {
        _uiState.update { it.copy(bottomSheetState = JurorListBottomSheetState.None) }
    }
}

data class JurorListUiState(
    val jurors: List<MateUiModel> = emptyList(),
    val inviteCode: String = "",
    val isLoading: Boolean = false,
    val dialogState: JurorListDialogState = JurorListDialogState.None,
    val bottomSheetState: JurorListBottomSheetState = JurorListBottomSheetState.None
)

sealed interface JurorListDialogState {
    data object None : JurorListDialogState
    data object Invite : JurorListDialogState
    data class DeleteConfirm(val jurorId: Long) : JurorListDialogState
}

sealed interface JurorListBottomSheetState {
    data object None : JurorListBottomSheetState
    data class JurorAction(val jurorId: Long) : JurorListBottomSheetState
}

sealed interface JurorListEffect {
    data class ShowSnackBar(val msg: String) : JurorListEffect
}
