package com.smtm.pickle.domain.repository

import com.smtm.pickle.domain.model.mate.Mate
import com.smtm.pickle.domain.model.mate.MateId
import com.smtm.pickle.domain.model.mate.MateRequest

interface MateRepository {
    suspend fun getMates(): List<Mate>
    suspend fun requestMate(invitationCode: String): MateId
    suspend fun getReceivedMateRequests(): List<MateRequest>
}
