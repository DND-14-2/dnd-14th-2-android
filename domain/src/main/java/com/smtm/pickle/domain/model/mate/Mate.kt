package com.smtm.pickle.domain.model.mate

@JvmInline
value class MateId(val value: Long)

/** 내 친구(배심원) */
data class Mate(
    val id: MateId,
    val nickname: String,
    val invitationCode: String,
    val verdictCount: Int,
)

/** 받은 친구 요청 */
data class MateRequest(
    val id: MateId,
    val nickname: String,
    val invitationCode: String,
)

/** 친구 요청 수락 거절 */
enum class MateStatus { Accepted, Rejected }
