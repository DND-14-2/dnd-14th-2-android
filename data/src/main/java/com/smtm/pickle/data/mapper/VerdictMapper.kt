package com.smtm.pickle.data.mapper

import com.smtm.pickle.data.source.remote.model.verdict.RemoteJurorVerdict
import com.smtm.pickle.data.source.remote.model.verdict.RemoteLedgerEntryInfo
import com.smtm.pickle.data.source.remote.model.verdict.RemoteMyVerdict
import com.smtm.pickle.data.source.remote.model.verdict.RemoteVerdictMate
import com.smtm.pickle.data.source.remote.model.verdict.RemoteVerdictType
import com.smtm.pickle.domain.model.verdict.JurorVerdict
import com.smtm.pickle.domain.model.verdict.LedgerEntryInfo
import com.smtm.pickle.domain.model.verdict.MyVerdict
import com.smtm.pickle.domain.model.verdict.VerdictId
import com.smtm.pickle.domain.model.verdict.VerdictMate
import com.smtm.pickle.domain.model.verdict.VerdictType

fun RemoteVerdictType.toDomain(): VerdictType = when (this) {
    RemoteVerdictType.Guilty -> VerdictType.Guilty
    RemoteVerdictType.NotGuilty -> VerdictType.NotGuilty
    RemoteVerdictType.Pending -> VerdictType.Pending
}

fun VerdictType.toRemote(): RemoteVerdictType = when (this) {
    VerdictType.Guilty -> RemoteVerdictType.Guilty
    VerdictType.NotGuilty -> RemoteVerdictType.NotGuilty
    VerdictType.Pending -> RemoteVerdictType.Pending
}

fun RemoteVerdictMate.toDomain() = VerdictMate(
    id = id,
    nickname = nickname,
    level = level,
    invitationCode = invitationCode,
)

fun RemoteLedgerEntryInfo.toDomain() = LedgerEntryInfo(
    id = id,
    amount = amount,
    category = category.toDomain(),
    paymentMethod = paymentMethod.toDomain(),
    description = description,
)

fun RemoteJurorVerdict.toDomain() = JurorVerdict(
    id = VerdictId(id),
    defendantInfo = defendantInfo.toDomain(),
    ledgerEntryInfo = ledgerEntryInfo.toDomain(),
    verdictType = verdictType.toDomain(),
)

fun RemoteMyVerdict.toDomain() = MyVerdict(
    id = VerdictId(id),
    ledgerEntryInfo = ledgerEntryInfo.toDomain(),
    jurorInfo = juror.toDomain(),
    verdictType = verdictType.toDomain(),
)
