package com.smtm.pickle.data.source.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ledger_table")
data class LedgerEntity(
    @PrimaryKey
    val id: Long,
    val type: String,
    val amount: Long,
    val category: String,
    val description: String?,
    val occurredOn: Long,
    val paymentMethod: String,
    val memo: String?
)