package com.smtm.pickle.data.source.remote.model.verdict

import kotlinx.serialization.Serializable

@Serializable
data class RemoteJurorVerdict(
    val id: Long,
    val defendantInfo: RemoteMate,
    val ledgerEntryInfo: RemoteLedgerEntryInfo,
    val verdictType: RemoteVerdictType,
)
