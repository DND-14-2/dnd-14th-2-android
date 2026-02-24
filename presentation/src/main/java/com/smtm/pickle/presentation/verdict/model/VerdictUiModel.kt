package com.smtm.pickle.presentation.verdict.model

import com.smtm.pickle.domain.model.verdict.Verdict
import com.smtm.pickle.domain.model.verdict.VerdictResult
import com.smtm.pickle.domain.model.verdict.VerdictStatus
import com.smtm.pickle.presentation.common.model.ledger.LedgerUiModel
import com.smtm.pickle.presentation.common.model.ledger.toUiModel
import java.time.LocalDateTime

data class VerdictUiModel(
    val id: Long,
    val ledger: LedgerUiModel,
    val defendant: MateUiModel,
    val status: VerdictStatus,
    val result: VerdictResult? = null,
    val createdAt: LocalDateTime,
    val isNew: Boolean = false,
)

fun Verdict.toUiModel(): VerdictUiModel = VerdictUiModel(
    id = id,
    ledger = ledger.toUiModel(),
    defendant = defendant.toUiModel(),
    status = status,
    result = result,
    createdAt = createdAt
)
