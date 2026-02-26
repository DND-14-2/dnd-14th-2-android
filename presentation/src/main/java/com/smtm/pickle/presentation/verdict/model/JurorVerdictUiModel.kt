package com.smtm.pickle.presentation.verdict.model

import com.smtm.pickle.domain.model.verdict.JurorVerdict
import com.smtm.pickle.domain.model.verdict.VerdictType

data class JurorVerdictUiModel(
    val id: Long,
    val defendant: MateUiModel,
    val ledgerEntry: LedgerEntryUiModel,
    val verdictType: VerdictType,
)

fun JurorVerdict.toUiModel() = JurorVerdictUiModel(
    id = id.value,
    defendant = defendantInfo.toUiModel(),
    ledgerEntry = ledgerEntryInfo.toUiModel(),
    verdictType = verdictType,
)
