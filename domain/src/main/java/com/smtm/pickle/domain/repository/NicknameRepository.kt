package com.smtm.pickle.domain.repository

import kotlinx.coroutines.flow.Flow

interface NicknameRepository {

    suspend fun saveNickname(nickname: String)

    suspend fun getNickname(): String

    fun observeNickname(): Flow<String?>
}
