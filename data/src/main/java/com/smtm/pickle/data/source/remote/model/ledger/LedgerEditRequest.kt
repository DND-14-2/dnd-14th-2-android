package com.smtm.pickle.data.source.remote.model.ledger

import kotlinx.serialization.Serializable

@Serializable
data class LedgerEditRequest(
    val amount: Long,
    val type: RemoteLedgerType,
    val category: RemoteLedgerCategory,
    val description: String,
    val occurredOn: String,
    val paymentMethod: RemotePaymentMethod,
    val memo: String?,
)
