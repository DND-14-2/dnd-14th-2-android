package com.smtm.pickle.data.source.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.smtm.pickle.data.di.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileDataStore @Inject constructor(
    @Profile private val dataStore: DataStore<Preferences>
) {
    private object PreferencesKeys {
        val NICKNAME = stringPreferencesKey("nickname")
    }

    suspend fun changeNickname(nickname: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.NICKNAME] = nickname
        }
    }

    fun observeNickname(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.NICKNAME] ?: ""
        }
    }
}
