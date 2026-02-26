package com.smtm.pickle.data.source.remote.model.verdict

import com.smtm.pickle.data.source.remote.model.ledger.RemoteLedgerCategory
import com.smtm.pickle.data.source.remote.model.ledger.RemotePaymentMethod
import kotlinx.serialization.Serializable

@Serializable
data class RemoteLedgerEntryInfo(
    val id: Long,
    val amount: Long,
    val category: RemoteLedgerCategory,
    val paymentMethod: RemotePaymentMethod,
    val description: String,
)
