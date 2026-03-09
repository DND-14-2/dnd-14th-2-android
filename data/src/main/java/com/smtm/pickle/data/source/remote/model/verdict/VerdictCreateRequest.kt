package com.smtm.pickle.data.source.remote.model.verdict

import kotlinx.serialization.Serializable

@Serializable
data class VerdictCreateRequest(
    val ledgerEntryId: Long,
)
