package com.smtm.pickle.presentation.verdict.model

import com.smtm.pickle.domain.model.verdict.JurorInfo
import com.smtm.pickle.domain.model.verdict.VerdictResult
import com.smtm.pickle.domain.model.verdict.VerdictStatus
import com.smtm.pickle.presentation.common.model.ledger.LedgerUiModel
import java.time.LocalDateTime

data class VerdictUiModel(
    val id: Long,
    val ledger: LedgerUiModel,
    val juror: JurorInfo,
    val status: VerdictStatus,
    val result: VerdictResult? = null,
    val createdAt: LocalDateTime,
)
