package com.smtm.pickle.domain.model.verdict

import com.smtm.pickle.domain.model.ledger.LedgerCategory
import com.smtm.pickle.domain.model.ledger.PaymentMethod

@JvmInline
value class VerdictId(val value: Long)

/** 피고인(친구) 정보 — 심판 응답에 포함되는 경량 모델 */
data class DefendantInfo(
    val id: Long,
    val nickname: String,
    val level: Int,
    val invitationCode: String,
)

/** 소비 내역 정보 — 심판 응답에 포함되는 경량 모델 */
data class LedgerEntryInfo(
    val id: Long,
    val amount: Long,
    val category: LedgerCategory,
    val paymentMethod: PaymentMethod,
    val description: String,
)

/** 배심원으로서 판결해야 할 심판 */
data class JurorVerdict(
    val id: VerdictId,
    val defendantInfo: DefendantInfo,
    val ledgerEntryInfo: LedgerEntryInfo,
    val verdictType: VerdictType,
)

/** 내가 요청한 소비 심판 */
data class MyVerdict(
    val id: VerdictId,
    val ledgerEntryInfo: LedgerEntryInfo,
    val verdictType: VerdictType,
)

enum class VerdictType { Guilty, NotGuilty, Pending, }
