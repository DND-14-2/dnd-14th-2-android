package com.smtm.pickle.data.repository

import com.smtm.pickle.data.source.local.datastore.PreferencesDataStore
import com.smtm.pickle.data.source.local.datastore.ProfileDataStore
import com.smtm.pickle.data.source.remote.api.UserApi
import com.smtm.pickle.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val preferencesDataStore: PreferencesDataStore,
    private val profileDataStore: ProfileDataStore
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

    override suspend fun getInvitationCode(): String {
        var invitationCode = profileDataStore.getInvitationCode()
        if (invitationCode == null) {
            val profile = userApi.getProfile()
            profileDataStore.setInvitationCode(profile.invitationCode)
            invitationCode = profile.invitationCode
        }

        return invitationCode
    }
}
