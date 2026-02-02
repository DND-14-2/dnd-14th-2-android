package com.smtm.pickle.data.source.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.smtm.pickle.data.di.Preference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesDataStore @Inject constructor(
    @Preference private val dataStore: DataStore<Preferences>
) {
    private object PreferencesKeys {
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        val IS_FIRST_LOGIN = booleanPreferencesKey("is_first_login")
    }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.ONBOARDING_COMPLETED] = completed
        }
    }

    fun isOnboardingCompleted(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.ONBOARDING_COMPLETED] ?: false
        }
    }

    suspend fun setFirstLogin(isFirstLogin: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_FIRST_LOGIN] = isFirstLogin
        }
    }

    fun isFirstLogin(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.IS_FIRST_LOGIN] ?: true
        }
    }
}
