package com.smtm.pickle.presentation.common.utils

import com.smtm.pickle.presentation.common.constant.InviteCodeValidation
import com.smtm.pickle.presentation.common.constant.NicknameValidation.AVAILABLE_LENGTH
import com.smtm.pickle.presentation.common.extension.isValidInviteCodeFormat
import com.smtm.pickle.presentation.common.extension.isValidNicknameFormat
import com.smtm.pickle.presentation.designsystem.components.textfield.model.InputState

object InputStateUtils {

    fun validateNicknameFormat(nickname: String, oldNickname: String? = null): InputState {
        if (nickname.isBlank()) return InputState.Idle
        if (oldNickname == nickname) return InputState.Error("기존 닉네임이랑 동일해요.")
        if (nickname.length > AVAILABLE_LENGTH) return InputState.Error("최대 5자 이내로 설정해주세요.")
        if (!nickname.isValidNicknameFormat()) return InputState.Error("특수 문자 및 영어 대문자는 사용할 수 없어요.")
        return InputState.Success(null)
    }

    fun validateInviteCodeFormat(inviteCode: String): InputState {
        if (inviteCode.isBlank()) return InputState.Idle
        if (inviteCode.length != InviteCodeValidation.AVAILABLE_LENGTH) return InputState.Error("6자로 입력해주세요")
        if (!inviteCode.isValidInviteCodeFormat()) return InputState.Error("영어 대문자로 입력해주세요")
        return InputState.Success(null)
    }
}
