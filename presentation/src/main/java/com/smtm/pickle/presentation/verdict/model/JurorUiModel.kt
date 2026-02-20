package com.smtm.pickle.presentation.verdict.model

import com.smtm.pickle.domain.model.verdict.Juror

data class JurorUiModel(
    val id: Long,
    val nickname: String,
)

fun Juror.toUiModel(): JurorUiModel = JurorUiModel(
    id = id,
    nickname = nickname,
)
