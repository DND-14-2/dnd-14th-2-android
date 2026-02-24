package com.smtm.pickle.domain.repository

import com.smtm.pickle.domain.model.mate.Mate
import com.smtm.pickle.domain.model.mate.MateId
import com.smtm.pickle.domain.model.mate.MateRequest
import com.smtm.pickle.domain.model.mate.MateStatus

interface MateRepository {

    /** 친구 요청 전송 */
    suspend fun inviteMate(invitationCode: String): MateId

    /** 친구(배심원) 목록 조회 */
    suspend fun getMates(): List<Mate>

    /** 받은 친구 요청 목록 조회 */
    suspend fun getReceivedMateRequests(): List<MateRequest>

    /** 받은 친구 요청 수락 거절 */
    suspend fun updateMateStatus(mateId: MateId, status: MateStatus)
}
