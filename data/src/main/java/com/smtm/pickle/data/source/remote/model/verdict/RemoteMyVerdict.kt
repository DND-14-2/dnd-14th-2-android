package com.smtm.pickle.data.source.remote.model.verdict

import kotlinx.serialization.Serializable

@Serializable
data class RemoteMyVerdict(
    val id: Long,
    val ledgerEntryInfo: RemoteLedgerEntryInfo,
    val verdictType: RemoteVerdictType,
)
