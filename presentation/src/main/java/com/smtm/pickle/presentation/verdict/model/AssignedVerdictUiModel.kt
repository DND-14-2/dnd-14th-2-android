package com.smtm.pickle.presentation.verdict.model

import com.smtm.pickle.domain.model.verdict.JurorVerdict

/** 내 판결: 지인이 나에게 요청한 심판 UI 모델 */
data class AssignedVerdictUiModel(
    val id: Long,
    val defendant: MateUiModel,
    val ledgerEntry: LedgerEntryUiModel,
    val verdictType: VerdictTypeUiModel,
)

fun JurorVerdict.toUiModel() = AssignedVerdictUiModel(
    id = id.value,
    defendant = defendantInfo.toUiModel(),
    ledgerEntry = ledgerEntryInfo.toUiModel(),
    verdictType = verdictType.toUiModel(),
)
