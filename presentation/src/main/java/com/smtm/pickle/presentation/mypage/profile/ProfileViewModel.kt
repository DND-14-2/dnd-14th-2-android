package com.smtm.pickle.presentation.mypage.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.usecase.user.ObserveNicknameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val DEFAULT_NICKNAME = "유저 닉네임"

@HiltViewModel
class ProfileViewModel @Inject constructor(
    getNicknameUseCase: ObserveNicknameUseCase
) : ViewModel() {

    private val _effect = MutableSharedFlow<ProfileEffect>(replay = 0)
    val effect: SharedFlow<ProfileEffect> = _effect

    val nickname: StateFlow<String> = getNicknameUseCase()
        .map { it ?: DEFAULT_NICKNAME }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DEFAULT_NICKNAME
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
}

sealed interface ProfileEffect {
    data object NavigateToBack : ProfileEffect
    data object NavigateToNicknameSetting : ProfileEffect
}
