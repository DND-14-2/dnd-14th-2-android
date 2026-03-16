package com.smtm.pickle.data.di

import com.smtm.pickle.data.repository.AuthRepositoryImpl
import com.smtm.pickle.data.repository.LedgerRepositoryImpl
import com.smtm.pickle.data.repository.UserRepositoryImpl
import com.smtm.pickle.domain.repository.AuthRepository
import com.smtm.pickle.domain.repository.LedgerRepository
import com.smtm.pickle.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindLedgerRepository(impl: LedgerRepositoryImpl): LedgerRepository
}
