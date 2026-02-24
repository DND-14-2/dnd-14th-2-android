package com.smtm.pickle.data.repository

import com.smtm.pickle.data.mapper.toDomain
import com.smtm.pickle.data.source.remote.api.MateApi
import com.smtm.pickle.data.source.remote.model.mate.InviteMateRequest
import com.smtm.pickle.domain.model.mate.MateId
import com.smtm.pickle.domain.repository.MateRepository
import javax.inject.Inject

class MateRepositoryImpl @Inject constructor(
    private val mateApi: MateApi,
) : MateRepository {

    override suspend fun inviteMate(invitationCode: String): MateId {
        return mateApi.inviteMate(InviteMateRequest(invitationCode)).toDomain()
    }
}
