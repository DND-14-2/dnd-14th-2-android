package com.smtm.pickle.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.smtm.pickle.data.source.local.database.entity.LedgerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LedgerDao {

    @Query(
        """
            SELECT * FROM ledger_table
            WHERE occurredOn BETWEEN :fromEpochDay AND :toEpochDay
            ORDER BY occurredOn DESC
        """
    )
    fun observeLedgers(fromEpochDay: Long, toEpochDay: Long): Flow<List<LedgerEntity>>

    @Query("SELECT * FROM ledger_table WHERE id = :id")
    fun observeLedger(id: Long): Flow<LedgerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: LedgerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<LedgerEntity>)

    @Update
    suspend fun update(entity: LedgerEntity)

    @Query("DELETE FROM ledger_table WHERE id = :id")
    suspend fun delete(id: Long)
}
