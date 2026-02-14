package com.smtm.pickle.presentation.mypage.profile.nicknamesetting

import com.smtm.pickle.presentation.designsystem.components.textfield.model.InputState

data class NicknameSettingUiState(
    val editingNickname: String = "",
    val inputState: InputState = InputState.Idle,
    val isNicknameModified: Boolean = false,
)
