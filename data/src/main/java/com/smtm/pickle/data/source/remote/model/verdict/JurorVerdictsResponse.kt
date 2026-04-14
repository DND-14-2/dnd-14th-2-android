package com.smtm.pickle.data.source.remote.model.verdict

import kotlinx.serialization.Serializable

@Serializable
data class JurorVerdictsResponse(
    val jurorVerdicts: List<RemoteJurorVerdict>,
)
