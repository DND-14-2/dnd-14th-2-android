package com.smtm.pickle.domain.repository

import com.smtm.pickle.domain.model.mate.Mate
import com.smtm.pickle.domain.model.mate.MateId
import com.smtm.pickle.domain.model.mate.ReceivedMate

interface MateRepository {

    suspend fun inviteMate(invitationCode: String): MateId

    suspend fun getMates(): List<Mate>

    suspend fun getReceivedMates(): List<ReceivedMate>
}
