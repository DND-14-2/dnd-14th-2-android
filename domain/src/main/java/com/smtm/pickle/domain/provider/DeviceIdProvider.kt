package com.smtm.pickle.domain.provider

interface DeviceIdProvider {

    suspend fun getOrCreate(): String
}
