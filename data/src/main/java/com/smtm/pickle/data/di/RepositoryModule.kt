package com.smtm.pickle.data.di

import com.smtm.pickle.data.repository.AuthRepositoryImpl
import com.smtm.pickle.domain.repository.AuthRepository
import com.smtm.pickle.data.repository.UserRepositoryImpl
import com.smtm.pickle.domain.repository.UserRepository
import com.smtm.pickle.data.repository.FakeNicknameRepository
import com.smtm.pickle.domain.repository.NicknameRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindNicknameRepository(impl: FakeNicknameRepository): NicknameRepository
}
