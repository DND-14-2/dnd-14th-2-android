package com.smtm.pickle.domain.model.verdict

data class JurorRelationStats(
    val totalVerdictCount: Int = 0,
    val myGuiltyCount: Int = 0,
    val opponentGuiltyCount: Int = 0,
)
