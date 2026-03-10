package com.smtm.pickle.data.source.remote.model.mate

import kotlinx.serialization.Serializable

@Serializable
data class RemoteMateRequest(
    val mateId: Long,
    val nickname: String,
    val invitationCode: String,
)
