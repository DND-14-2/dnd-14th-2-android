package com.smtm.pickle.data.repository

import com.smtm.pickle.data.common.utils.apiCall
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

    override suspend fun inviteMate(invitationCode: String): MateId = apiCall {
        val response = mateApi.inviteMate(InviteMateRequest(invitationCode))
        MateId(response.mateId)
    }

    override suspend fun getMates(): List<Mate> {
        return mateApi.getMates().map { it.toDomain() }
    }

    override suspend fun getReceivedMateRequests(): List<MateRequest> {
        return mateApi.getReceivedMateRequests().map { it.toDomain() }
    }

    override suspend fun updateMateRequestStatus(mateId: MateId, status: MateStatus): MateId {
        val response = mateApi.updateMateRequestStatus(
            mateId = mateId.value,
            request = MateStatusUpdateRequest(status.toRemote()),
        )

        return MateId(response.mateId)
    }
}
