package com.smtm.pickle.domain.model.auth

data class User(
    val id: String,
    val email: String,
    val nickname: String,
    val profileImgUrl: String?,
)
