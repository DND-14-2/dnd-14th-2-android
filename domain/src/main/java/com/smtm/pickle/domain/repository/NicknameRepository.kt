package com.smtm.pickle.domain.repository

import kotlinx.coroutines.flow.Flow

interface NicknameRepository {

    suspend fun isNicknameAvailable(nickname: String): Boolean

    suspend fun saveNickname(nickname: String)

    fun getNickname(): Flow<String>

}
