package com.smtm.pickle.domain.model.user

data class User(
    val id: Long,
    val nickname: String,
    val level: Int,
    val invitationCode: String,
)
