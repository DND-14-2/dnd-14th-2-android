package com.smtm.pickle.data.mapper

import com.smtm.pickle.data.source.remote.model.mate.RemoteMate
import com.smtm.pickle.data.source.remote.model.mate.RemoteMateRequest
import com.smtm.pickle.data.source.remote.model.mate.RemoteMateStatus
import com.smtm.pickle.domain.model.mate.Mate
import com.smtm.pickle.domain.model.mate.MateId
import com.smtm.pickle.domain.model.mate.MateRequest
import com.smtm.pickle.domain.model.mate.MateStatus

fun RemoteMate.toDomain(): Mate = Mate(
    id = MateId(mateId),
    nickname = nickname,
    invitationCode = invitationCode,
    verdictCount = verdictCount,
)

fun RemoteMateRequest.toDomain() = MateRequest(
    id = MateId(mateId),
    nickname = nickname,
    invitationCode = invitationCode,
)

fun MateStatus.toRemote(): RemoteMateStatus = when (this) {
    MateStatus.Accepted -> RemoteMateStatus.Accepted
    MateStatus.Rejected -> RemoteMateStatus.Rejected
}
