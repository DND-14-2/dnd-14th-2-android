package com.smtm.pickle.presentation.login.nickname

import com.smtm.pickle.presentation.designsystem.components.textfield.model.InputState

/**
 * 닉네임 관련 UI 상태
 * @property nickname 닉네임
 * @property inputState 텍스트 필드 입력 상태
 * @property isCheckingDuplicate 중복 확인 중 여부
 * @property isAvailable 중복 확인 결과
 */
data class NicknameUiState(
    val nickname: String = "",
    val inputState: InputState = InputState.Idle,
    val isCheckingDuplicate: Boolean = false,
    val isAvailable: Boolean? = null,
) {
    /** 버튼 활성화 여부 */
    val canSubmit: Boolean
        get() = inputState is InputState.Success && isAvailable == true
}
