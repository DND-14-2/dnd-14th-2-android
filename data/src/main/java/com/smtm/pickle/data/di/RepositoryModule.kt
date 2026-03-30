package com.smtm.pickle.data.di

import com.smtm.pickle.data.repository.AuthRepositoryImpl
import com.smtm.pickle.data.repository.LedgerRepositoryImpl
import com.smtm.pickle.data.repository.MateRepositoryImpl
import com.smtm.pickle.data.repository.VerdictRepositoryImpl
import com.smtm.pickle.data.repository.UserRepositoryImpl
import com.smtm.pickle.domain.repository.AuthRepository
import com.smtm.pickle.domain.repository.LedgerRepository
import com.smtm.pickle.domain.repository.MateRepository
import com.smtm.pickle.domain.repository.VerdictRepository
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

    @Binds
    @Singleton
    abstract fun bindMateRepository(impl: MateRepositoryImpl): MateRepository

    @Binds
    @Singleton
    abstract fun bindVerdictRepository(impl: VerdictRepositoryImpl): VerdictRepository
}
