package com.smtm.pickle.data.source.remote.model.user

import kotlinx.serialization.Serializable

@Serializable
data class RemoteUser(
    val id: Long,
    val nickname: String,
    val level: Int,
    val invitationCode: String,
)
