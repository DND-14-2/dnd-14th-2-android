package com.smtm.pickle.data.repository

import com.smtm.pickle.data.mapper.toDomain
import com.smtm.pickle.data.mapper.toRemote
import com.smtm.pickle.data.source.remote.api.MateApi
import com.smtm.pickle.data.source.remote.model.mate.InviteMateRequest
import com.smtm.pickle.data.source.remote.model.mate.MateStatusUpdateRequest
import com.smtm.pickle.domain.model.mate.Mate
import com.smtm.pickle.domain.model.mate.MateId
import com.smtm.pickle.domain.model.mate.MateRequest
import com.smtm.pickle.domain.model.mate.MateStatus
import com.smtm.pickle.domain.repository.MateRepository
import javax.inject.Inject

class MateRepositoryImpl @Inject constructor(
    private val mateApi: MateApi,
) : MateRepository {

    override suspend fun inviteMate(invitationCode: String): MateId {
        val response = mateApi.inviteMate(InviteMateRequest(invitationCode))
        return MateId(response.mateId)
    }

    override suspend fun getMates(): List<Mate> {
        return mateApi.getMates().map { it.toDomain() }
    }

    override suspend fun getReceivedMateRequests(): List<MateRequest> {
        return mateApi.getReceivedMateRequests().map { it.toDomain() }
    }

    override suspend fun updateMateStatus(mateId: MateId, status: MateStatus) {
        mateApi.updateMateStatus(
            mateId = mateId.value,
            request = MateStatusUpdateRequest(status.toRemote()),
        )
    }
}
