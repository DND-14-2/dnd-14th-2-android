package com.smtm.pickle.domain.model.auth

data class AuthToken(
    val access: String,
    val refresh: String,
)
