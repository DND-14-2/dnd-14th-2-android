package com.smtm.pickle.domain.repository

import com.smtm.pickle.domain.model.ledger.Ledger
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface LedgerRepository {

    fun observeLedgers(from: LocalDate, to: LocalDate): Flow<List<Ledger>>

    // 동기화: 외부 데이터 -> Room 캐시 보장
    suspend fun ensureSynced(from: LocalDate, to: LocalDate)
}