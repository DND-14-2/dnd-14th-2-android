package com.smtm.pickle.data.source.remote.model.ledger

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LedgersResponse(
    @SerialName("result")
    val ledgers: List<RemoteLedger>,
    @SerialName("start")
    val start: String,
    @SerialName("end")
    val end: String,
)
