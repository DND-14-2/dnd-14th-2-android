package com.smtm.pickle.presentation.mypage.profile.nicknamesetting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.usecase.user.GetNicknameUseCase
import com.smtm.pickle.domain.usecase.user.SaveNicknameUseCase
import com.smtm.pickle.presentation.common.constant.NicknameValidation
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
class NicknameSettingViewModel @Inject constructor(
    private val saveNicknameUseCase: SaveNicknameUseCase,
    private val getNicknameUseCase: GetNicknameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NicknameSettingUiState())
    val uiState: StateFlow<NicknameSettingUiState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<NicknameSettingEffect>(replay = 0)
    val effect: SharedFlow<NicknameSettingEffect> = _effect.asSharedFlow()

    private var savedNickname: String? = null

    init {
        initializeNickname()
    }

    private fun initializeNickname() {
        viewModelScope.launch {
            getNicknameUseCase()
                .onSuccess { nickname ->
                    savedNickname = nickname

                    _uiState.update {
                        it.copy(editingNickname = nickname ?: "유저 닉네임")
                    }
                }.onFailure { e ->
                    Timber.e(e, "닉네임 초기화 실패")
                }
        }
    }

    fun onNicknameChanged(nickname: String) {
        val correctNickname = nickname.take(NicknameValidation.MAX_NICKNAME_LENGTH)

        _uiState.update { state ->
            state.copy(
                editingNickname = correctNickname,
                inputState = InputStateUtils.validateNicknameFormat(correctNickname, savedNickname),
                isNicknameModified = true
            )
        }
    }

    fun saveNickname() {
        viewModelScope.launch {
            saveNicknameUseCase(_uiState.value.editingNickname)
                .onSuccess {
                    _effect.emit(NicknameSettingEffect.NavigateToBack)
                }
                .onFailure { e ->
                    Timber.e(e, "닉네임 저장 실패")
                }
        }
    }

    fun onBackClick() {
        viewModelScope.launch {
            _effect.emit(NicknameSettingEffect.NavigateToBack)
        }
    }
}

sealed interface NicknameSettingEffect {
    data object NavigateToBack : NicknameSettingEffect
}
