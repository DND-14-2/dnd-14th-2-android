package com.smtm.pickle.presentation.verdict.model

import com.smtm.pickle.domain.model.mate.Mate
import com.smtm.pickle.domain.model.mate.MateRequest
import com.smtm.pickle.domain.model.verdict.MateInfo

data class MateUiModel(
    val id: Long,
    val nickname: String,
    val level: Int = 0,
    val invitationCode: String = "",
    val verdictCount: Int = 0,
)

fun Mate.toUiModel(): MateUiModel = MateUiModel(
    id = id.value,
    nickname = nickname,
    invitationCode = invitationCode,
    verdictCount = verdictCount
)

fun MateInfo.toUiModel() = MateUiModel(
    id = id,
    nickname = nickname,
    level = level,
    invitationCode = invitationCode,
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
