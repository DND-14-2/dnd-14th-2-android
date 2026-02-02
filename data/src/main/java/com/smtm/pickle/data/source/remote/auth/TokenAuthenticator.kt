package com.smtm.pickle.data.source.remote.auth

import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

/** 서버 응답이 401일때 OkHttp가 자동으로 실행 */
@Singleton
class TokenAuthenticator @Inject constructor(
    private val tokenRefresher: TokenRefresher
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {

        // 순환 참조 방지
        if (response.priorResponse != null) return null

        val failedToken = response.request.header("Authorization")
            ?.removePrefix("Bearer ")
            ?: return null

        // RefreshToken으로 새로운 Token 발급
        val newToken = runBlocking {
            tokenRefresher.refreshTokenIfNeeded(failedToken)
        } ?: return null

        // 401 엑세스 토큰 만료로 실패한 요청에 새 토큰을 넣어 재시도
        return response.request.newBuilder()
            .header("Authorization", "Bearer ${newToken.access}")
            .build()
    }
}
