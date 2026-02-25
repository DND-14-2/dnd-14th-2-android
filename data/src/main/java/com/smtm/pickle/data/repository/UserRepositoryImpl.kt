package com.smtm.pickle.data.repository

import com.smtm.pickle.data.source.local.datastore.PreferencesDataStore
import com.smtm.pickle.data.source.local.datastore.ProfileDataStore
import com.smtm.pickle.data.source.remote.api.UserApi
import com.smtm.pickle.data.source.remote.model.user.NicknameRequest
import com.smtm.pickle.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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

    override suspend fun saveNickname(nickname: String) {
        val changedNickname = userApi.changeNickname(NicknameRequest(nickname)).nickname
        profileDataStore.changeNickname(changedNickname)
    }

    override suspend fun getNickname(): String {
        val profile = userApi.getProfile()
        profileDataStore.changeNickname(profile.nickname)
        return profile.nickname
    }

    override fun observeNickname(): Flow<String?> = profileDataStore.observeNickname()
}
