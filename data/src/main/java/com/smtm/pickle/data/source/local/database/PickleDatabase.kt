package com.smtm.pickle.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smtm.pickle.data.source.local.database.dao.LedgerDao
import com.smtm.pickle.data.source.local.database.entity.LedgerEntity

@Database(
    entities = [LedgerEntity::class],
    version = 1,
    exportSchema = true
)
abstract class PickleDatabase : RoomDatabase() {

    abstract fun ledgerDao(): LedgerDao
}