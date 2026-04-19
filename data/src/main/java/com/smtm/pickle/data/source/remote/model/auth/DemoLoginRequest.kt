package com.smtm.pickle.data.source.remote.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class DemoLoginRequest(
    val deviceId: String,
)
