package com.smtm.pickle.data.source.remote.auth

import com.smtm.pickle.data.source.remote.api.RefreshTokenApi
import com.smtm.pickle.domain.model.auth.AuthToken
import com.smtm.pickle.domain.provider.TokenProvider
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRefresher @Inject constructor(
    private val tokenProvider: TokenProvider,
    private val refreshApi: RefreshTokenApi,
) {
    private val refreshLock = Mutex()

    suspend fun refreshTokenIfNeeded(failedToken: String): AuthToken? {

        // Access 코드를 이미 획득했는지 판단
        tokenProvider.getToken()
            ?.takeIf { it.access != failedToken }
            ?.let { return it }

        return refreshLock.withLock {

            tokenProvider.getToken()
                ?.takeIf { it.access != failedToken }
                ?.let { return it }


            // 사용자에게 토큰이 있으면 획득, 없으면
            val refreshToken = tokenProvider.getRefreshToken()
                ?: return@withLock null

            val response = runCatching {
                refreshApi.refreshToken("Bearer $refreshToken")
            }.getOrElse { e ->
                if (e is CancellationException) throw e
                // 리프레시 토큰 만료 또는 무효일 때만 로그아웃 처리
                if (e is HttpException && (e.code() == 401 || e.code() == 400)) {
                    tokenProvider.clearToken()
                }
                return@withLock null
            }

            AuthToken(
                access = response.accessToken,
                refresh = response.refreshToken
            ).also {
                // 토큰 저장
                tokenProvider.saveToken(it)
            }
        }
    }
}
