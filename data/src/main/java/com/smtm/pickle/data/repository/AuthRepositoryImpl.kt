package com.smtm.pickle.data.repository

import com.smtm.pickle.data.mapper.toDomain
import com.smtm.pickle.data.source.remote.api.AuthService
import com.smtm.pickle.data.source.remote.datasource.GoogleAuthDataSource
import com.smtm.pickle.data.source.remote.model.LoginRequest
import com.smtm.pickle.domain.model.auth.AuthToken
import com.smtm.pickle.domain.model.auth.SocialLoginType
import com.smtm.pickle.domain.provider.TokenProvider
import com.smtm.pickle.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val tokenProvider: TokenProvider,
    private val googleAuthDataSource: GoogleAuthDataSource,
) : AuthRepository {

    /**  카카오, 구글 소셜 로그인 */
    override suspend fun socialLogin(
        token: String,
        type: SocialLoginType
    ): Result<AuthToken> = runCatching {

        val response = authService.socialLogin(
            request = LoginRequest(
                idToken = token,
                provider = type.name
            )
        )

        val authToken = response.toDomain()

        tokenProvider.saveToken(authToken)

        authToken
    }

    /** Google 토큰 획득 -> 로그인 */
    override suspend fun loginWithGoogle(): Result<AuthToken> =
        googleAuthDataSource.getIdToken().mapCatching { idToken ->
            socialLogin(
                token = idToken,
                type = SocialLoginType.GOOGLE
            ).getOrThrow()
        }

}
