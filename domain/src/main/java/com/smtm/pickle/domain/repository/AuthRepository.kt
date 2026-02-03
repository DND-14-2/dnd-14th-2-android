package com.smtm.pickle.domain.repository

import com.smtm.pickle.domain.model.auth.AuthToken
import com.smtm.pickle.domain.model.auth.SocialLoginType

interface AuthRepository {

    suspend fun socialLogin(
        token: String,
        type: SocialLoginType,
    ): Result<AuthToken>

    suspend fun loginWithGoogle(): Result<AuthToken>

}
