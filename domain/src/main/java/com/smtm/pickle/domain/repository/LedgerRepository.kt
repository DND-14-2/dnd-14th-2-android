package com.smtm.pickle.domain.repository

import com.smtm.pickle.domain.model.ledger.Ledger
import com.smtm.pickle.domain.model.ledger.LedgerCategory
import com.smtm.pickle.domain.model.ledger.LedgerType
import com.smtm.pickle.domain.model.ledger.PaymentMethod
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface LedgerRepository {

    fun observeLedgers(from: LocalDate, to: LocalDate): Flow<List<Ledger>>

    fun observeLedger(ledgerId: Long): Flow<Ledger>

    suspend fun getLedger(ledgerId: Long): Ledger?

    // 동기화: 외부 데이터 -> Room 캐시 보장
    suspend fun ensureSynced(from: LocalDate, to: LocalDate)
    suspend fun syncLedger(id: Long)

    suspend fun createLedger(
        amount: Long,
        type: LedgerType,
        category: LedgerCategory,
        description: String,
        occurredOn: LocalDate,
        paymentMethod: PaymentMethod,
        memo: String?,
    )

    suspend fun updateLedger(
        ledgerId: Long,
        amount: Long,
        type: LedgerType,
        category: LedgerCategory,
        description: String,
        occurredOn: LocalDate,
        paymentMethod: PaymentMethod,
        memo: String?,
    )

    suspend fun deleteLedger(id: Long)
}
