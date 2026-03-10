package com.smtm.pickle.presentation.login.nickname

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.usecase.user.SaveNicknameUseCase
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
                    _effect.emit(NicknameEffect.NavigateToMain)
                }
                .onFailure { e ->
                    Timber.e(e, "닉네임 저장 실패")
                }
        }
    }

    fun onBackClick() {
        viewModelScope.launch {
            _effect.emit(NicknameEffect.NavigateToMain)
        }
    }
}

sealed interface NicknameEffect {
    data object NavigateToMain : NicknameEffect
}
