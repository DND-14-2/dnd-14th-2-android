package com.smtm.pickle.data.mapper

import com.smtm.pickle.data.source.remote.model.LoginResponse
import com.smtm.pickle.domain.model.auth.AuthToken

fun LoginResponse.toDomain(): AuthToken = AuthToken(
    access = accessToken,
    refresh = refreshToken
)
