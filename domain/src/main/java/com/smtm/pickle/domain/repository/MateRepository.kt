package com.smtm.pickle.domain.repository

import com.smtm.pickle.domain.model.mate.Mate
import com.smtm.pickle.domain.model.mate.MateId

interface MateRepository {

    suspend fun inviteMate(invitationCode: String): MateId

    suspend fun getMates(): List<Mate>
}
