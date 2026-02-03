package com.smtm.pickle.data.source.local.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import timber.log.Timber
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject
import javax.inject.Singleton

/** 토큰 키 암호화 클래스 */
@Singleton
class TokenEncryption @Inject constructor() {

    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    private val keyAlias = "PickleTokenKey"
    private val transformation = "AES/GCM/NoPadding"
    private val gcmTagLength = 128

    /** Keystore에서 AES 키를 가져오거나 없으면 생성합니다 */
    private fun getOrCreateKey(): SecretKey {
        // 기존 키가 있는지 확인
        val existingKey = keyStore.getEntry(keyAlias, null) as? KeyStore.SecretKeyEntry
        if (existingKey != null) {
            return existingKey.secretKey
        }

        // 새 키 생성
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            "AndroidKeyStore"
        )

        val keySpec = KeyGenParameterSpec.Builder(
            keyAlias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setUserAuthenticationRequired(false) // 사용자 인증 불필요
            .build()

        keyGenerator.init(keySpec)
        return keyGenerator.generateKey()
    }

    /**
     * 토큰을 암호화합니다
     * `@return` "암호화된데이터:IV" 형식의 문자열
     */
    fun encrypt(plainText: String): String? {
        return try {
            val cipher = Cipher.getInstance(transformation)
            cipher.init(Cipher.ENCRYPT_MODE, getOrCreateKey())

            val iv = cipher.iv // 초기화 벡터
            val encryptedBytes = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))

            // 암호화된 데이터와 IV를 함께 Base64로 인코딩하여 저장
            val encryptedBase64 = Base64.encodeToString(encryptedBytes, Base64.NO_WRAP)
            val ivBase64 = Base64.encodeToString(iv, Base64.NO_WRAP)

            "$encryptedBase64:$ivBase64"
        } catch (e: Exception) {
            Timber.e(e, "암호화 실패")
            null
        }
    }

    /**
     * 암호화된 토큰을 복호화합니다
     * `@param` encryptedData "암호화된데이터:IV" 형식의 문자열
     */
    fun decrypt(encryptedData: String): String? {
        return try {
            val parts = encryptedData.split(":")
            if (parts.size != 2) {
                Timber.e("잘못된 암호화 데이터 형식")
                return null
            }

            val encryptedBytes = Base64.decode(parts[0], Base64.NO_WRAP)
            val iv = Base64.decode(parts[1], Base64.NO_WRAP)

            val cipher = Cipher.getInstance(transformation)
            val spec = GCMParameterSpec(gcmTagLength, iv)
            cipher.init(Cipher.DECRYPT_MODE, getOrCreateKey(), spec)

            val decryptedBytes = cipher.doFinal(encryptedBytes)
            String(decryptedBytes, Charsets.UTF_8)
        } catch (e: Exception) {
            Timber.e(e, "복호화 실패")
            null
        }
    }
}
