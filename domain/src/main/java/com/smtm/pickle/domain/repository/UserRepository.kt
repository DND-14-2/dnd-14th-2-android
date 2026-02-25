package com.smtm.pickle.domain.repository


interface UserRepository {

    suspend fun setOnboardingCompleted(completed: Boolean)

    suspend fun getOnboardingStatus(): Boolean

    suspend fun setFirstLogin(isFirstLogin: Boolean)

    suspend fun getFirstLogin(): Boolean

    suspend fun getInvitationCode(): String
}
