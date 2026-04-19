package com.smtm.pickle.data.di

import com.smtm.pickle.data.source.local.provider.DeviceIdProviderImpl
import com.smtm.pickle.data.source.local.provider.TokenProviderImpl
import com.smtm.pickle.domain.provider.DeviceIdProvider
import com.smtm.pickle.domain.provider.TokenProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindTokenProvider(
        tokenProviderImpl: TokenProviderImpl
    ): TokenProvider

    @Binds
    abstract fun bindDeviceIdProvider(
        deviceIdProviderImpl: DeviceIdProviderImpl
    ): DeviceIdProvider
}
