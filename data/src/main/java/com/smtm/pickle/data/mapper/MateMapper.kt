package com.smtm.pickle.data.mapper

import com.smtm.pickle.data.source.remote.model.mate.RemoteMate
import com.smtm.pickle.domain.model.mate.Mate
import com.smtm.pickle.domain.model.mate.MateId

fun RemoteMate.toDomain(): Mate = Mate(
    id = MateId(mateId),
    nickname = nickname,
    invitationCode = invitationCode,
    verdictCount = verdictCount,
)
