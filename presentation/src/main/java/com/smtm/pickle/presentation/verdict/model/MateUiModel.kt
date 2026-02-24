package com.smtm.pickle.presentation.verdict.model

import com.smtm.pickle.domain.model.mate.Mate
import com.smtm.pickle.domain.model.mate.MateRequest

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

data class MateRequestUiModel(
    val id: Long,
    val nickname: String,
    val invitationCode: String
)

fun MateRequest.toUiModel(): MateRequestUiModel = MateRequestUiModel(
    id = id.value,
    nickname = nickname,
    invitationCode = invitationCode
)
