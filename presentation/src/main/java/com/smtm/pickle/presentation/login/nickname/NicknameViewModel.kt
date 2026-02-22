package com.smtm.pickle.presentation.login.nickname

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.usecase.nickname.SaveNicknameUseCase
import com.smtm.pickle.presentation.common.constant.NicknameValidation.MAX_NICKNAME_LENGTH
import com.smtm.pickle.presentation.common.utils.NicknameUtils
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
    private val saveNicknameUseCase: SaveNicknameUseCase
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
                inputState = NicknameUtils.validateNicknameFormat(correctNickname),
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

    fun onInviteClick() {
        _uiState.update { it.copy(dialogState = NicknameDialogState.InputInviteCode) }
    }

    fun onSkipInviteClick() {
        _uiState.update { it.copy(dialogState = NicknameDialogState.ShareInviteCode) }
    }

    fun onAlreadyHasCodeClick() {
        _uiState.update { it.copy(dialogState = NicknameDialogState.Welcome) }
    }

    fun onStartClick() {
        _uiState.update { it.copy(dialogState = NicknameDialogState.None) }
        emitNavigateToMainEffect()
    }

    fun onDialogDismiss() {
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
