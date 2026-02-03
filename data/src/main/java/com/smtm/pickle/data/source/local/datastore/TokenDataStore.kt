package com.smtm.pickle.data.source.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.smtm.pickle.data.di.Token
import com.smtm.pickle.data.source.local.security.TokenEncryption
import com.smtm.pickle.domain.model.auth.AuthToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenDataStore @Inject constructor(
    @Token private val dataStore: DataStore<Preferences>,
    private val tokenEncryption: TokenEncryption
) {
    private object PreferencesKeys {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    private val dataStoreFlow = dataStore.data.catch { e ->
        if (e is IOException) emit(emptyPreferences()) else throw e
    }

    suspend fun saveToken(token: AuthToken) {
        val encryptedAccess = tokenEncryption.encrypt(token.access)
        val encryptedRefresh = tokenEncryption.encrypt(token.refresh)
        if (encryptedRefresh == null || encryptedAccess == null) {
            Timber.e("토큰 암호화 실패")
            return
        }

        dataStore.edit { preferences ->
            preferences[PreferencesKeys.ACCESS_TOKEN] = encryptedAccess
            preferences[PreferencesKeys.REFRESH_TOKEN] = encryptedRefresh
        }
    }

    suspend fun getToken(): AuthToken? = dataStoreFlow.first().getAuthToken()

    suspend fun getRefreshToken(): String? {
        val encryptedToken = dataStoreFlow.first()[PreferencesKeys.REFRESH_TOKEN]
        return encryptedToken?.let { tokenEncryption.decrypt(it) }
    }

    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.ACCESS_TOKEN)
            preferences.remove(PreferencesKeys.REFRESH_TOKEN)
        }
    }

    fun getAccessTokenFlow(): Flow<String?> = dataStoreFlow.map { preferences ->
        preferences[PreferencesKeys.ACCESS_TOKEN]?.let { tokenEncryption.decrypt(it) }
    }

    fun getTokenFlow(): Flow<AuthToken?> = dataStoreFlow.map { preferences ->
        preferences.getAuthToken()
    }

    private fun Preferences.getAuthToken(): AuthToken? {
        val encryptedAccess = this[PreferencesKeys.ACCESS_TOKEN]
        val encryptedRefresh = this[PreferencesKeys.REFRESH_TOKEN]

        if (encryptedAccess == null || encryptedRefresh == null) return null

        val access = tokenEncryption.decrypt(encryptedAccess)
        val refresh = tokenEncryption.decrypt(encryptedRefresh)

        return if (access != null && refresh != null) {
            AuthToken(access, refresh)
        } else {
            Timber.e("토큰 복호화 실패")
            null
        }
    }
}
