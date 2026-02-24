package com.smtm.pickle.presentation.verdict.model

import com.smtm.pickle.domain.model.mate.Mate

data class MateUiModel(
    val id: Long,
    val nickname: String,
    val invitationCode: String = "",
    val verdictCount: Int = 0,
)

fun Mate.toUiModel(): MateUiModel = MateUiModel(
    id = id.value,
    nickname = nickname,
    invitationCode = invitationCode,
    verdictCount = verdictCount
)
