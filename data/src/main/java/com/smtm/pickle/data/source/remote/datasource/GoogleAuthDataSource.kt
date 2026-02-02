package com.smtm.pickle.data.source.remote.datasource

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.smtm.pickle.data.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleAuthDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val credentialManager = CredentialManager.create(context)

    suspend fun getIdToken(): Result<String> = try {
        val googleLoginOption = GetSignInWithGoogleOption.Builder(
            serverClientId = BuildConfig.GOOGLE_WEB_CLIENT_ID
        )
            .setNonce(generateNonce()) // 재전송 공격 방지 일회성 암호표
            .build()

        // 패스키나 일반 로그인 옵션 추가
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleLoginOption) // 구글 로그인 옵션만
            .build()

        val response = credentialManager.getCredential(
            request = request,
            context = context
        )

        // credential 객체에서 ID 토큰 추출
        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(response.credential.data)

        // 서버 전송용 구글 고유 식별자(사용자 검증용)
        val idToken = googleIdTokenCredential.idToken

        Result.success(idToken)
    } catch (e: GetCredentialCancellationException) { // TODO: 추후 도메인 오류들로 변경
        Timber.e(e, "구글 로그인 - 사용자 취소")
        Result.failure(e)
    } catch (e: GetCredentialException) {
        Timber.e(e, "구글 로그인 - 인증 실패")
        Result.failure(e)
    } catch (e: Exception) {
        Timber.e(e, "구글 로그인 중 알 수 없는 오류 발생")
        Result.failure(e)
    }

    /** 보안 강화용 Nonce 생성 - 재사용 공격 방지 */
    private fun generateNonce(): String {
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val digest = messageDigest.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}
