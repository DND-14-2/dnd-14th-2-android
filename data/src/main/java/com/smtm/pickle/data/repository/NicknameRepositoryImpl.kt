package com.smtm.pickle.data.repository

import com.smtm.pickle.data.source.local.datastore.ProfileDataStore
import com.smtm.pickle.data.source.remote.api.UserApi
import com.smtm.pickle.data.source.remote.model.user.NicknameRequest
import com.smtm.pickle.domain.repository.NicknameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NicknameRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val profileDataStore: ProfileDataStore
) : NicknameRepository {

    override suspend fun saveNickname(nickname: String) {
        userApi.changeNickname(NicknameRequest(nickname))
        profileDataStore.changeNickname(nickname)
    }

    override suspend fun getNickname(): String {
        val nickname = userApi.getProfile().nickname
        profileDataStore.changeNickname(nickname)
        return nickname
    }

    override fun observeNickname(): Flow<String> = profileDataStore.observeNickname()
}
