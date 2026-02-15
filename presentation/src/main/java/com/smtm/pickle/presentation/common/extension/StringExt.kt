package com.smtm.pickle.presentation.common.extension

import com.smtm.pickle.presentation.common.constant.NicknameValidation

fun String.isValidNicknameFormat(): Boolean {
    return this.matches(NicknameValidation.FORMAT_REGEX)
}
