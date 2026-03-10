package com.smtm.pickle.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun setOnboardingCompleted(completed: Boolean)

    suspend fun getOnboardingStatus(): Boolean

    suspend fun setFirstLogin(isFirstLogin: Boolean)

    suspend fun getFirstLogin(): Boolean

    suspend fun getInvitationCode(): String

    suspend fun saveNickname(nickname: String)

    suspend fun getNickname(): String

    fun observeNickname(): Flow<String?>
}
