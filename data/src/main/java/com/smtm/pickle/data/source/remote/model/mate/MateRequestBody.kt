package com.smtm.pickle.data.source.remote.model.mate

import kotlinx.serialization.Serializable

@Serializable
data class MateRequestBody(
    val invitationCode: String,
)
