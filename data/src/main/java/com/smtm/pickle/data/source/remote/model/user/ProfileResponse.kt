package com.smtm.pickle.data.source.remote.model.user

import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    val nickname: String
)
