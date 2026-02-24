package com.smtm.pickle.presentation.login.nickname

import com.smtm.pickle.presentation.designsystem.components.textfield.model.InputState

/**
 * 닉네임 관련 UI 상태
 * @property nickname 닉네임
 * @property inputState 텍스트 필드 입력 상태
 */
data class NicknameUiState(
    val nickname: String = "",
    val inputState: InputState = InputState.Idle,
    val dialogState: NicknameDialogState = NicknameDialogState.None,
)

sealed interface NicknameDialogState {
    data object None : NicknameDialogState
    data object InviteIntroduction : NicknameDialogState
    data class InputInvitationCode(
        val errorMessage: String? = null
    ) : NicknameDialogState
    data object ShareInviteCode : NicknameDialogState
    data object Welcome : NicknameDialogState
}
