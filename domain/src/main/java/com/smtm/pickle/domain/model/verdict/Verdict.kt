package com.smtm.pickle.domain.model.verdict

import com.smtm.pickle.domain.model.ledger.Ledger
import java.time.LocalDateTime

data class Verdict(
    val id: Long,
    val ledger: Ledger,
    val juror: JurorInfo,
    val status: VerdictStatus,
    val result: VerdictResult? = null,
    val createdAt: LocalDateTime,
)
