package com.smtm.pickle.domain.model.verdict

data class JurorInfo(
    val id: Long,
    val nickname: String,
    val badgeCode: String,
    val badgeName: String,
    val jurorCode: String? = null,
)
