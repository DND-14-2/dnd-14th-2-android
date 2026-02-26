package com.smtm.pickle.data.source.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.smtm.pickle.data.di.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileDataStore @Inject constructor(
    @Profile private val dataStore: DataStore<Preferences>
) {
    private object PreferencesKeys {
        val NICKNAME = stringPreferencesKey("nickname")
        val INVITATION_CODE = stringPreferencesKey("invitation_code")
    }

    suspend fun changeNickname(nickname: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.NICKNAME] = nickname
        }
    }

    fun observeNickname(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.NICKNAME]
        }
    }

    suspend fun setInvitationCode(invitationCode: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.INVITATION_CODE] = invitationCode
        }
    }

    suspend fun getInvitationCode(): String? {
        return dataStore.data.first()[PreferencesKeys.INVITATION_CODE]
    }
}
