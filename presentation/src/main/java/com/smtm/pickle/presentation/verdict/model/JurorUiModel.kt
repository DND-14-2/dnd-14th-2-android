package com.smtm.pickle.presentation.verdict.model

import com.smtm.pickle.domain.model.verdict.User

data class JurorUiModel(
    val id: Long,
    val nickname: String,
)

fun User.toUiModel(): JurorUiModel = JurorUiModel(
    id = id,
    nickname = nickname,
)
