package com.smtm.pickle.presentation.mypage.profile.nicknamesetting

import com.smtm.pickle.presentation.designsystem.components.textfield.model.InputState

data class NicknameSettingUiState(
    val editingNickname: String = "",
    val inputState: InputState = InputState.Idle,
    val isCheckingDuplicate: Boolean = false,
    val isAvailable: Boolean? = null,
    val isNicknameModified: Boolean = false,
) {
    val canSubmit: Boolean
        get() = inputState is InputState.Success && isAvailable == true
}
