package com.smtm.pickle.data.repository

import com.smtm.pickle.data.mapper.toDomain
import com.smtm.pickle.data.mapper.toRemote
import com.smtm.pickle.data.source.remote.api.VerdictApi
import com.smtm.pickle.data.source.remote.model.verdict.VerdictCreateRequest
import com.smtm.pickle.data.source.remote.model.verdict.VerdictJudgeRequest
import com.smtm.pickle.domain.model.verdict.JurorVerdict
import com.smtm.pickle.domain.model.verdict.MyVerdict
import com.smtm.pickle.domain.model.verdict.VerdictType
import com.smtm.pickle.domain.repository.VerdictRepository
import javax.inject.Inject

class VerdictRepositoryImpl @Inject constructor(
    private val verdictApi: VerdictApi,
) : VerdictRepository {

    override suspend fun getJurorVerdicts(): List<JurorVerdict> {
        return verdictApi.getJurorVerdicts().jurorVerdicts.map { it.toDomain() }
    }

    override suspend fun getMyVerdicts(): List<MyVerdict> {
        return verdictApi.getMyVerdicts().verdicts.map { it.toDomain() }
    }

    override suspend fun requestVerdict(ledgerEntryId: Long) {
        verdictApi.requestVerdict(VerdictCreateRequest(ledgerEntryId))
    }

    override suspend fun judgeVerdict(verdictId: Long, verdictType: VerdictType): JurorVerdict {
        val request = VerdictJudgeRequest(verdictType.toRemote())
        return verdictApi.judgeVerdict(verdictId, request).toDomain()
    }
}
