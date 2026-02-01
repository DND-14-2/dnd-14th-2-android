package com.smtm.pickle.data.source.remote.datasource

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class KakaoAuthDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {

    suspend fun getAccessToken(): Result<String> = suspendCancellableCoroutine { continuation ->
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            when {
                error != null -> {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        continuation.resume(Result.failure(error))
                    }
                }

                token?.idToken != null -> {
                    continuation.resume(Result.success(token.idToken!!))
                }

                else -> {
                    continuation.resume(Result.failure(IllegalStateException("Token not returned")))
                }
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        if (continuation.isActive) continuation.resume(Result.failure(error))
                        return@loginWithKakaoTalk
                    }
                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else {
                    callback(token, null)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }
}
