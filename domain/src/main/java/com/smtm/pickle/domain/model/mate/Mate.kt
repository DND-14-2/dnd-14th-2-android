package com.smtm.pickle.domain.model.mate

@JvmInline
value class MateId(val value: Long)

data class Mate(
    val id: MateId,
    val nickname: String,
    val invitationCode: String,
    val verdictCount: Int,
)

data class ReceivedMate(
    val id: MateId,
    val nickname: String,
    val invitationCode: String,
)
