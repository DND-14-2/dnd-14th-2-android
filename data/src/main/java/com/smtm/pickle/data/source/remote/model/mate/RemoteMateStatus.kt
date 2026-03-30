package com.smtm.pickle.data.source.remote.model.mate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class RemoteMateStatus {
    @SerialName("ACCEPTED")
    Accepted,

    @SerialName("REJECTED")
    Rejected,
}
