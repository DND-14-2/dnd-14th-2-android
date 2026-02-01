package com.smtm.pickle.presentation.common.auth

import android.content.Context
import androidx.compose.runtime.Stable
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.smtm.pickle.presentation.R
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import timber.log.Timber
import javax.inject.Inject

@Stable
@ActivityScoped
class KakaoLoginManager @Inject constructor(
    @ActivityContext private val context: Context
) {
    fun login(
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val resultHandler: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            when {
                token?.idToken != null -> {
                    onSuccess(token.idToken!!)
                }

                error != null -> {
                    Timber.e(error, "카카오 로그인 실패")
                    onFailure(context.getString(R.string.login_error_general))
                }

                else -> {
                    Timber.e("알 수 없는 오류 발생")
                    onFailure(context.getString(R.string.error_unknown))
                }
            }
        }

        if (!UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            loginWithKakaoAccount(resultHandler)
            return
        }

        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            when {
                token?.idToken != null -> {
                    onSuccess(token.idToken!!)
                }

                // 사용자 취소도 로그인 실패 처리
                error is ClientError && error.reason == ClientErrorCause.Cancelled -> {
                    Timber.e(error, "카카오 로그인 취소")
                    onFailure(context.getString(R.string.login_error_cancelled))
                }

                // 카카오톡 실패 → 카카오계정 로그인 fallback
                error != null -> {
                    Timber.e(error, "카카오 로그인 실패, 카카오계정으로 로그인")
                    loginWithKakaoAccount(resultHandler)
                }

                else -> {
                    onFailure(context.getString(R.string.error_unknown))
                }
            }
        }
    }

    fun logout(onCompleted: () -> Unit) {
        UserApiClient.instance.logout { error ->
            onCompleted()
        }
    }

    private fun loginWithKakaoAccount(resultHandler: (OAuthToken?, Throwable?) -> Unit) {
        UserApiClient.instance.loginWithKakaoAccount(
            context = context,
            callback = resultHandler
        )
    }
}
