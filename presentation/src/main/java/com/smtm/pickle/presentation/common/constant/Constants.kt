package com.smtm.pickle.presentation.common.constant

object NicknameValidation {
    val FORMAT_REGEX = Regex("^[a-z0-9가-힣]+$")

    const val AVAILABLE_LENGTH = 5
    const val MAX_NICKNAME_LENGTH = 30
}
