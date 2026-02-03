package com.smtm.pickle.data.repository

import com.smtm.pickle.domain.repository.NicknameRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeNicknameRepository @Inject constructor() : NicknameRepository {

    private val savedNicknames = mutableSetOf<String>()

    override suspend fun isNicknameAvailable(nickname: String): Boolean =
        nickname !in savedNicknames

    override suspend fun saveNickname(nickname: String) {
        savedNicknames.add(nickname)
    }
}
