package com.smtm.pickle.data.source.remote.model.verdict

import kotlinx.serialization.Serializable

@Serializable
data class RemoteMate(
    val id: Long,
    val nickname: String,
    val level: Int,
    val invitationCode: String,
)
