package com.smtm.pickle.presentation.common.utils

import com.smtm.pickle.presentation.common.constant.NicknameValidation.AVAILABLE_LENGTH
import com.smtm.pickle.presentation.common.extension.isValidNicknameFormat
import com.smtm.pickle.presentation.designsystem.components.textfield.model.InputState

object NicknameUtils {
    fun validateNicknameFormat(nickname: String): InputState {
        if (nickname.isBlank()) return InputState.Idle
        if (nickname.length > AVAILABLE_LENGTH) return InputState.Error("최대 5자 이내로 설정해주세요.")
        if (!nickname.isValidNicknameFormat()) return InputState.Error("특수 문자 및 영어 대문자는 사용할 수 없어요.")
        return InputState.Success(null)
    }
}
