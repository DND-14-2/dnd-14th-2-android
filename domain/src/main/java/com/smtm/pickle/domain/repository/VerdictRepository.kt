package com.smtm.pickle.domain.repository

import com.smtm.pickle.domain.model.verdict.JurorVerdict
import com.smtm.pickle.domain.model.verdict.MyVerdict
import com.smtm.pickle.domain.model.verdict.VerdictType

interface VerdictRepository {
    /** 배심원으로서 판결해야 할 심판 목록 조회 */
    suspend fun getJurorVerdicts(): List<JurorVerdict>

    /** 내 소비 심판 목록 조회 */
    suspend fun getMyVerdicts(): List<MyVerdict>

    /** 소비 심판 요청 */
    suspend fun requestVerdict(ledgerEntryId: Long)

    /** 소비 심판 판결하기 */
    suspend fun judgeVerdict(verdictId: Long, verdictType: VerdictType): JurorVerdict
}
