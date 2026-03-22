package com.smtm.pickle.domain.event

import kotlinx.coroutines.flow.Flow

/** 세션 상태 변화를 앱 전역에 전달하는 이벤트 버스 */
interface SessionEventBus {
    val sessionExpired: Flow<Unit>
    suspend fun emitSessionExpired()
}
