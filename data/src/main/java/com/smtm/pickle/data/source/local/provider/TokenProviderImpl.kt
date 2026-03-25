package com.smtm.pickle.data.source.local.provider

import com.smtm.pickle.data.source.local.datastore.TokenDataStore
import com.smtm.pickle.domain.model.auth.AuthState
import com.smtm.pickle.domain.model.auth.AuthToken
import com.smtm.pickle.domain.provider.TokenProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenProviderImpl @Inject constructor(
    private val tokenDataStore: TokenDataStore
) : TokenProvider {

    @Volatile
    private var cachedToken: AuthToken? = null

    private val _authState = MutableStateFlow<AuthState>(AuthState.Authenticated)

    override suspend fun init() {
        cachedToken = tokenDataStore.getToken()
    }

    override suspend fun saveToken(token: AuthToken) {
        cachedToken = token
        tokenDataStore.saveToken(token)
    }

    override suspend fun getToken(): AuthToken? {
        return cachedToken ?: tokenDataStore.getToken().also { cachedToken = it }
    }

    override suspend fun getRefreshToken(): String? =
        tokenDataStore.getRefreshToken()

    override suspend fun clearToken() {
        cachedToken = null
        tokenDataStore.clearToken()
        _authState.value = AuthState.Unauthenticated
    }

    override suspend fun expireSession() {
        cachedToken = null
        tokenDataStore.clearToken()
        _authState.value = AuthState.SessionExpired
    }

    override fun getCachedToken(): AuthToken? = cachedToken

    override fun getTokenFlow(): Flow<AuthToken?> =
        tokenDataStore.getTokenFlow()

    override fun getAccessTokenFlow(): Flow<String?> =
        tokenDataStore.getAccessTokenFlow()

    override fun getAuthStateFlow(): Flow<AuthState> =
        _authState.asStateFlow()

}
