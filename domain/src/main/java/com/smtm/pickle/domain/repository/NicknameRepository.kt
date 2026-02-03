package com.smtm.pickle.domain.repository

interface NicknameRepository {

    suspend fun isNicknameAvailable(nickname: String): Boolean

    suspend fun saveNickname(nickname: String)

}
