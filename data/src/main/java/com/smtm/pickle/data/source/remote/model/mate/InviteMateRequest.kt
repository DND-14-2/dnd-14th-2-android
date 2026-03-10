package com.smtm.pickle.data.source.remote.model.mate

import kotlinx.serialization.Serializable

@Serializable
data class InviteMateRequest(
    val invitationCode: String,
)
