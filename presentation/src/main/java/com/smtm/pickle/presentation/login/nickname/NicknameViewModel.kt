package com.smtm.pickle.presentation.login.nickname

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.usecase.nickname.CheckNicknameAvailableUseCase
import com.smtm.pickle.domain.usecase.nickname.SaveNicknameUseCase
import com.smtm.pickle.presentation.common.constant.NicknameValidation.AVAILABLE_LENGTH
import com.smtm.pickle.presentation.common.constant.NicknameValidation.MAX_NICKNAME_LENGTH
import com.smtm.pickle.presentation.common.extension.isValidNicknameFormat
import com.smtm.pickle.presentation.designsystem.components.textfield.model.InputState
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
class NicknameViewModel @Inject constructor(
    private val checkNicknameAvailableUseCase: CheckNicknameAvailableUseCase,
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
                inputState = validateFormat(correctNickname),
                isCheckingDuplicate = false,
                isAvailable = null
            )
        }
    }

    fun checkDuplicate() {
        val state = uiState.value
        if (state.inputState !is InputState.Success) return
        val requestedNickname = state.nickname

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isCheckingDuplicate = true,
                    isAvailable = null,
                )
            }

            val isAvailable = checkNicknameAvailableUseCase(requestedNickname)
            _uiState.update {
                if (it.nickname != requestedNickname) return@update it

                it.copy(
                    isCheckingDuplicate = false,
                    isAvailable = isAvailable,
                    inputState = if (isAvailable) {
                        InputState.Success("사용 가능한 닉네임이에요!")
                    } else {
                        InputState.Error("이미 사용중인 닉네임이에요.")
                    }
                )
            }
        }
    }

    fun saveNickname() {
        viewModelScope.launch {
            runCatching {
                saveNicknameUseCase(uiState.value.nickname)
            }.onSuccess {
                _effect.emit(NicknameEffect.NavigateToMain)
            }
        }
    }

    fun onBackClick() {
        viewModelScope.launch {
            _effect.emit(NicknameEffect.NavigateToMain)
        }
    }

    private fun validateFormat(nickname: String): InputState {
        if (nickname.isBlank()) return InputState.Idle
        if (nickname.length > AVAILABLE_LENGTH) return InputState.Error("최대 5자 이내로 설정해주세요.")
        if (!nickname.isValidNicknameFormat()) return InputState.Error("특수 문자 및 영어 대문자는 사용할 수 없어요.")
        return InputState.Success(null)
    }

    sealed interface NicknameEffect {
        data object NavigateToMain : NicknameEffect
    }
}
