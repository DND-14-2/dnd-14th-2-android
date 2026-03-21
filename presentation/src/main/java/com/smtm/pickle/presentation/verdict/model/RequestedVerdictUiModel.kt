package com.smtm.pickle.presentation.verdict.model

import com.smtm.pickle.domain.model.verdict.MyVerdict

/** 내 심판: 내가 요청한 심판 UI 모델 */
data class RequestedVerdictUiModel(
    val id: Long,
    val juror: MateUiModel,
    val ledgerEntry: LedgerEntryUiModel,
    val verdictType: VerdictTypeUiModel,
)

fun MyVerdict.toUiModel() = RequestedVerdictUiModel(
    id = id.value,
    juror = jurorInfo.toUiModel(),
    ledgerEntry = ledgerEntryInfo.toUiModel(),
    verdictType = verdictType.toUiModel(),
)
