package com.smtm.pickle.data.source.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val provider: String,  // GOOGLE or KAKAO
    val idToken: String,
)
