package com.smtm.pickle.presentation.login.nickname

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.usecase.mate.InviteMateUseCase
import com.smtm.pickle.domain.usecase.user.SaveNicknameUseCase
import com.smtm.pickle.domain.usecase.user.GetInvitationCodeUseCase
import com.smtm.pickle.presentation.common.constant.NicknameValidation.MAX_NICKNAME_LENGTH
import com.smtm.pickle.presentation.common.utils.InputStateUtils
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
class NicknameViewModel @Inject constructor(
    private val saveNicknameUseCase: SaveNicknameUseCase,
    private val inviteMateUseCase: InviteMateUseCase,
    private val getInvitationCodeUseCase: GetInvitationCodeUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(NicknameUiState())
    val uiState: StateFlow<NicknameUiState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<NicknameEffect>(replay = 0)
    val effect: SharedFlow<NicknameEffect> = _effect.asSharedFlow()


    /** onValueChange 콜백 함수 */
    fun onNicknameChanged(nickname: String) {
        val correctNickname = nickname.take(MAX_NICKNAME_LENGTH)

        _uiState.update {
            it.copy(
                nickname = correctNickname,
                inputState = InputStateUtils.validateNicknameFormat(correctNickname),
            )
        }
    }

    fun saveNickname() {
        viewModelScope.launch {
            saveNicknameUseCase(_uiState.value.nickname)
                .onSuccess {
                    _uiState.update { it.copy(dialogState = NicknameDialogState.InviteIntroduction) }
                }
                .onFailure { e ->
                    Timber.e(e, "닉네임 저장 실패")
                }
        }
    }

    fun onBackClick() {
        emitNavigateToMainEffect()
    }

    fun showInputInvitationCodeDialog() {
        _uiState.update { it.copy(dialogState = NicknameDialogState.InputInvitationCode()) }
    }

    fun showShareInvitationCodeDialog() {
        viewModelScope.launch {
            getInvitationCodeUseCase()
                .onSuccess { code ->
                    _uiState.update {
                        it.copy(dialogState = NicknameDialogState.ShareInvitationCode(code))
                    }
                }
                .onFailure { e ->
                    Timber.e(e, "초대코드 받기 실패")
                }
        }
    }

    fun showWelcomeDialog() {
        _uiState.update { it.copy(dialogState = NicknameDialogState.Welcome) }
    }

    fun inviteMate(invitationCode: String) {
        _uiState.update {
            it.copy(dialogState = NicknameDialogState.InputInvitationCode(errorMessage = null))
        }

        viewModelScope.launch {
            inviteMateUseCase(invitationCode)
                .onSuccess {
                    showWelcomeDialog()
                }
                .onFailure { e ->
                    Timber.e(e)
                    _uiState.update {
                        it.copy(
                            dialogState = NicknameDialogState.InputInvitationCode(
                                errorMessage = "초대코드를 확인해주세요"
                            )
                        )
                    }
                }
        }
    }

    fun onCompleteWelcome() {
        _uiState.update { it.copy(dialogState = NicknameDialogState.None) }
        emitNavigateToMainEffect()
    }

    private fun emitNavigateToMainEffect() {
        viewModelScope.launch {
            _effect.emit(NicknameEffect.NavigateToMain)
        }
    }
}

sealed interface NicknameEffect {
    data object NavigateToMain : NicknameEffect
}
