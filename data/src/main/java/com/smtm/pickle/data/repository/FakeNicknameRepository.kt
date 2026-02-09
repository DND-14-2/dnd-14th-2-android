package com.smtm.pickle.data.repository

import com.smtm.pickle.domain.repository.NicknameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeNicknameRepository @Inject constructor() : NicknameRepository {

    private val savedNicknames = mutableSetOf<String>()
    private val _nicknameFlow = MutableStateFlow("유저 닉네임")

    override suspend fun isNicknameAvailable(nickname: String): Boolean =
        nickname !in savedNicknames

    override suspend fun saveNickname(nickname: String) {
        savedNicknames.add(nickname)
        _nicknameFlow.value = nickname
    }

    override suspend fun getNickname(): String = _nicknameFlow.value

    override fun observeNickname(): Flow<String> = _nicknameFlow.asStateFlow()
}
