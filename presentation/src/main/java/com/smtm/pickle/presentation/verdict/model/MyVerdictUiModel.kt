package com.smtm.pickle.presentation.verdict.model

import com.smtm.pickle.domain.model.verdict.MyVerdict
import com.smtm.pickle.domain.model.verdict.VerdictType

/** 내가 요청한 소비 심판 UI 모델 */
data class MyVerdictUiModel(
    val id: Long,
    val ledgerEntry: LedgerEntryUiModel,
    val verdictType: VerdictType,
)

fun MyVerdict.toUiModel() = MyVerdictUiModel(
    id = id.value,
    ledgerEntry = ledgerEntryInfo.toUiModel(),
    verdictType = verdictType,
)
