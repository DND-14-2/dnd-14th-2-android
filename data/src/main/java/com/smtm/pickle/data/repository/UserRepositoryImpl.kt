package com.smtm.pickle.data.repository

import com.smtm.pickle.data.source.local.datastore.PreferencesDataStore
import com.smtm.pickle.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore
) : UserRepository {

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        preferencesDataStore.setOnboardingCompleted(completed)
    }

    override fun isOnboardingCompleted(): Flow<Boolean> {
        return preferencesDataStore.isOnboardingCompleted()
    }

    override suspend fun setFirstLogin(isFirstLogin: Boolean) {
        preferencesDataStore.setFirstLogin(isFirstLogin)
    }

    override fun isFirstLogin(): Flow<Boolean> {
        return preferencesDataStore.isFirstLogin()
    }
}
