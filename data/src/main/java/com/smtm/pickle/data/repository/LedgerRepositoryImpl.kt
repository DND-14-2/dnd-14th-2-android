package com.smtm.pickle.data.repository

import com.smtm.pickle.data.mapper.toDomain
import com.smtm.pickle.data.mapper.toEntity
import com.smtm.pickle.data.source.local.database.dao.LedgerDao
import com.smtm.pickle.data.source.remote.api.LedgerApi
import com.smtm.pickle.domain.model.ledger.Ledger
import com.smtm.pickle.domain.repository.LedgerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import timber.log.Timber
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

class LedgerRepositoryImpl @Inject constructor(
    private val ledgerDao: LedgerDao,
    private val ledgerApi: LedgerApi,
) : LedgerRepository {

    private val syncedMonths = mutableSetOf<YearMonth>()
    private val syncMutex = Mutex()

    override fun observeLedgers(from: LocalDate, to: LocalDate): Flow<List<Ledger>> {
        return ledgerDao
            .observeLedgers(fromEpochDay = from.toEpochDay(), toEpochDay = to.toEpochDay())
            .map { entities ->
                entities.mapNotNull { entity ->
                    try {
                        entity.toDomain()
                    } catch (e: Exception) {
                        Timber.e(e, "Invalid entity skipped: id=${entity.id}")
                        null
                    }
                }
            }
            .distinctUntilChanged()
    }

    override suspend fun ensureSynced(from: LocalDate, to: LocalDate) {
        syncMutex.withLock {
            val requestedMonths = generatedMonthRange(from, to)
            val unsyncedMonths = requestedMonths.filter { it !in syncedMonths }

            if (unsyncedMonths.isEmpty()) return

            val syncFrom = unsyncedMonths.min().atDay(1)
            val syncTo = unsyncedMonths.max().atEndOfMonth()

            val remoteLedgers = ledgerApi.getLedgerSummary(
                from = syncFrom.toString(),
                to = syncTo.toString()
            ).ledgers

            val ledgerEntities = remoteLedgers.mapNotNull { ledger ->
                try {
                    ledger.toEntity()
                } catch (e: Exception) {
                    Timber.e(e, "Failed to map remote ledger: id=${ledger.ledgerId}")
                    null
                }
            }

            ledgerDao.insertAll(ledgerEntities)
            syncedMonths.addAll(unsyncedMonths)
        }
    }

    private fun generatedMonthRange(from: LocalDate, to: LocalDate): List<YearMonth> {
        val months = mutableListOf<YearMonth>()
        var current = YearMonth.from(from)
        val end = YearMonth.from(to)
        while (current <= end) {
            months.add(current)
            current = current.plusMonths(1)
        }
        return months
    }
}
