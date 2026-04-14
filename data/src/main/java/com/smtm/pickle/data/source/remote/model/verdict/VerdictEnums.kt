package com.smtm.pickle.data.source.remote.model.verdict

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class RemoteVerdictType {
    @SerialName("GUILTY")
    Guilty,
    @SerialName("NOT_GUILTY")
    NotGuilty,
    @SerialName("PENDING")
    Pending,
}
