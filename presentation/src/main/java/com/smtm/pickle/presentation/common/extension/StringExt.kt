package com.smtm.pickle.presentation.common.extension

import com.smtm.pickle.presentation.common.constant.InviteCodeValidation
import com.smtm.pickle.presentation.common.constant.NicknameValidation

fun String.isValidNicknameFormat(): Boolean {
    return this.matches(NicknameValidation.FORMAT_REGEX)
}

fun String.isValidInviteCodeFormat(): Boolean {
    return this.matches(InviteCodeValidation.FORMAT_REGEX)
}
