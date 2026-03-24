package com.smtm.pickle.data.event

import com.smtm.pickle.domain.event.SessionEventBus
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionEventBusImpl @Inject constructor() : SessionEventBus {
    private val _channel = Channel<Unit>(Channel.CONFLATED)
    override val sessionExpired: Flow<Unit> = _channel.receiveAsFlow()
    override suspend fun emitSessionExpired() {
        _channel.trySend(Unit)
    }
}
