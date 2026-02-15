package com.smtm.pickle.data.source.remote.model.user

import kotlinx.serialization.Serializable

@Serializable
data class NicknameResponse(
    val nickname: String
)
