package com.smtm.pickle.data.repository

import com.smtm.pickle.data.source.local.datastore.PreferencesDataStore
import com.smtm.pickle.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore
) : UserRepository {

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        preferencesDataStore.setOnboardingCompleted(completed)
    }

    override suspend fun getOnboardingStatus(): Boolean {
        return preferencesDataStore.isOnboardingCompleted().first()
    }

    override suspend fun setFirstLogin(isFirstLogin: Boolean) {
        preferencesDataStore.setFirstLogin(isFirstLogin)
    }

    override suspend fun getFirstLogin(): Boolean {
        return preferencesDataStore.isFirstLogin().first()
    }
}
