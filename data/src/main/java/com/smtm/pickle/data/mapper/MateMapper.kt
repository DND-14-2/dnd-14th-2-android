package com.smtm.pickle.data.mapper

import com.smtm.pickle.data.source.remote.model.mate.RemoteMate
import com.smtm.pickle.data.source.remote.model.mate.RemoteReceivedMate
import com.smtm.pickle.domain.model.mate.Mate
import com.smtm.pickle.domain.model.mate.MateId
import com.smtm.pickle.domain.model.mate.ReceivedMate

fun RemoteMate.toDomain(): Mate = Mate(
    id = MateId(mateId),
    nickname = nickname,
    invitationCode = invitationCode,
    verdictCount = verdictCount,
)

fun RemoteReceivedMate.toDomain(): ReceivedMate = ReceivedMate(
    id = MateId(mateId),
    nickname = nickname,
    invitationCode = invitationCode,
)
