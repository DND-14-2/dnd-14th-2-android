package com.smtm.pickle.data.mapper

import com.smtm.pickle.data.source.remote.model.user.RemoteUser
import com.smtm.pickle.domain.model.user.User

fun RemoteUser.toDomain() = User(
    id = id,
    nickname = nickname,
    level = level,
    invitationCode = invitationCode,
)
