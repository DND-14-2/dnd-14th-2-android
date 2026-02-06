package com.smtm.pickle.presentation.mypage.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.usecase.nickname.CheckNicknameAvailableUseCase
import com.smtm.pickle.domain.usecase.nickname.GetNicknameUseCase
import com.smtm.pickle.domain.usecase.nickname.SaveNicknameUseCase
import com.smtm.pickle.presentation.common.constant.NicknameValidation
import com.smtm.pickle.presentation.common.utils.NicknameUtils
import com.smtm.pickle.presentation.designsystem.components.textfield.model.InputState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NicknameSettingViewModel @Inject constructor(
    private val checkNicknameAvailableUseCase: CheckNicknameAvailableUseCase,
    private val saveNicknameUseCase: SaveNicknameUseCase,
    private val getNicknameUseCase: GetNicknameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NicknameSettingUiState())
    val uiState: StateFlow<NicknameSettingUiState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<NicknameSettingEffect>(replay = 0)
    val effect: SharedFlow<NicknameSettingEffect> = _effect.asSharedFlow()

    init {
        initializeNickname()
    }

    private fun initializeNickname() {
        viewModelScope.launch {
            val currentNickname = getNicknameUseCase().first()
            _uiState.update {
                it.copy(editingNickname = currentNickname)
            }
        }
    }

    fun onNicknameChanged(nickname: String) {
        val correctNickname = nickname.take(NicknameValidation.MAX_NICKNAME_LENGTH)

        _uiState.update { state ->
            state.copy(
                editingNickname = correctNickname,
                inputState = NicknameUtils.validateNicknameFormat(correctNickname),
                isCheckingDuplicate = false,
                isAvailable = null,
                isNicknameModified = true
            )
        }
    }

    fun checkDuplicate() {
        val state = uiState.value
        if (state.inputState !is InputState.Success) return
        val requestedNickname = state.editingNickname

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isCheckingDuplicate = true,
                    isAvailable = null,
                )
            }

            val isAvailable = checkNicknameAvailableUseCase(requestedNickname)
                .onFailure { e -> Timber.e(e, "닉네임 중복 체크 실패") }
                .getOrDefault(false)

            _uiState.update {
                if (it.editingNickname != requestedNickname) return@update it

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
            saveNicknameUseCase(uiState.value.editingNickname)
                .onSuccess {
                    _effect.emit(NicknameSettingEffect.NavigateBack)
                }
                .onFailure { e ->
                    Timber.e(e, "닉네임 저장 실패")
                }
        }
    }

    sealed interface NicknameSettingEffect {
        data object NavigateBack : NicknameSettingEffect
    }
}
