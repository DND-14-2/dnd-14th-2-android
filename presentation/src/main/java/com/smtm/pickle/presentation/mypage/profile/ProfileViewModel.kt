package com.smtm.pickle.presentation.mypage.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.usecase.nickname.GetNicknameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    getNicknameUseCase: GetNicknameUseCase
) : ViewModel() {

    private val _effect = MutableSharedFlow<ProfileEffect>(replay = 0)
    val effect: SharedFlow<ProfileEffect> = _effect

    val nickname: StateFlow<String> = getNicknameUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "닉네임"
        )

    fun onNicknameEditClick() {
        viewModelScope.launch {
            _effect.emit(ProfileEffect.NavigateToNicknameSetting)
        }
    }

    fun onBackClick() {
        viewModelScope.launch {
            _effect.emit(ProfileEffect.NavigateToBack)
        }
    }

    sealed interface ProfileEffect {
        data object NavigateToBack : ProfileEffect
        data object NavigateToNicknameSetting : ProfileEffect
    }
}
