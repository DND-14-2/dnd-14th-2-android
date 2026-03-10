package com.smtm.pickle.presentation.verdict.jurorlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.usecase.mate.GetMatesUseCase
import com.smtm.pickle.domain.usecase.mate.GetReceivedMateRequestsUseCase
import com.smtm.pickle.domain.usecase.mate.InviteMateUseCase
import com.smtm.pickle.domain.usecase.user.GetInvitationCodeUseCase
import com.smtm.pickle.presentation.common.utils.InputStateUtils
import com.smtm.pickle.presentation.designsystem.components.textfield.model.InputState
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
    private val inviteMateUseCase: InviteMateUseCase,
    private val getReceivedMateRequestsUseCase: GetReceivedMateRequestsUseCase,
    private val getInvitationCodeUseCase: GetInvitationCodeUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(JurorListUiState())
    val uiState: StateFlow<JurorListUiState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<JurorListEffect>()
    val effect: SharedFlow<JurorListEffect> = _effect.asSharedFlow()


    init {
        loadMates()
        loadInvitationCode()
        checkHasReceivedRequests()
    }


    fun onResume() {
        loadMates()
        checkHasReceivedRequests()
    }

    fun onAddJurorClick() {
        _uiState.update {
            it.copy(dialogState = JurorListDialogState.InputInviteCode)
        }
    }

    fun onJurorInviteClick() {
        _uiState.update {
            it.copy(dialogState = JurorListDialogState.CopyInviteCode)
        }
    }

    fun onInputInviteCodeChanged(code: String) {
        _uiState.update {
            it.copy(
                inputInviteCode = code,
                inputInviteCodeState = if (code.isBlank()) {
                    InputState.Idle
                } else {
                    InputState.Success(null)
                }
            )
        }
    }

    fun onInputInviteActionDone() {
        _uiState.update { it.copy(inputInviteCodeState = InputState.Idle) }
    }

    fun onJurorMoreClick(jurorId: Long) {
        _uiState.update {
            it.copy(bottomSheetState = JurorListBottomSheetState.JurorDelete(jurorId))
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

    fun onInputInviteConfirmClick() {
        if (_uiState.value.isLoading) return

        val code = _uiState.value.inputInviteCode
        val validationState = InputStateUtils.validateInviteCodeFormat(code)

        if (validationState is InputState.Error) {
            _uiState.update { it.copy(inputInviteCodeState = validationState) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            inviteMateUseCase(code)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            inputInviteCode = ""
                        )
                    }
                    _effect.emit(JurorListEffect.ShowSnackBar("메이트 요청을 보냈어요"))
                    dismissDialog()
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            inputInviteCodeState = InputState.Error(e.message ?: "초대코드를 확인해주세요")
                        )
                    }
                }
        }
    }

    fun confirmDeleteJuror() {
        val currentState = _uiState.value.dialogState
        if (currentState is JurorListDialogState.DeleteConfirm) {
            val jurorId = currentState.jurorId
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, dialogState = JurorListDialogState.None) }
                // TODO: Juror 삭제 API 호출

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
        _uiState.update {
            it.copy(
                dialogState = JurorListDialogState.None,
                inputInviteCode = "",
                inputInviteCodeState = InputState.Idle
            )
        }
    }

    fun dismissBottomSheet() {
        _uiState.update { it.copy(bottomSheetState = JurorListBottomSheetState.None) }
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

    private fun checkHasReceivedRequests() {
        viewModelScope.launch {
            getReceivedMateRequestsUseCase()
                .onSuccess { requests ->
                    _uiState.update {
                        it.copy(hasReceivedMateRequests = requests.isNotEmpty())
                    }
                }
        }
    }

    private fun loadInvitationCode() {
        viewModelScope.launch {
            getInvitationCodeUseCase()
                .onSuccess { code ->
                    _uiState.update { it.copy(myInviteCode = code) }
                }
        }
    }
}

data class JurorListUiState(
    val jurors: List<MateUiModel> = emptyList(),
    val hasReceivedMateRequests: Boolean = false,
    val myInviteCode: String = "",
    val inputInviteCode: String = "",
    val inputInviteCodeState: InputState = InputState.Idle,
    val isLoading: Boolean = false,
    val dialogState: JurorListDialogState = JurorListDialogState.None,
    val bottomSheetState: JurorListBottomSheetState = JurorListBottomSheetState.None
)

sealed interface JurorListDialogState {
    data object None : JurorListDialogState
    data object CopyInviteCode : JurorListDialogState
    data object InputInviteCode : JurorListDialogState
    data class DeleteConfirm(val jurorId: Long) : JurorListDialogState
}

sealed interface JurorListBottomSheetState {
    data object None : JurorListBottomSheetState
    data class JurorDelete(val jurorId: Long) : JurorListBottomSheetState
}

sealed interface JurorListEffect {
    data class ShowSnackBar(val msg: String) : JurorListEffect
}
