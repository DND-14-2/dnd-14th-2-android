package com.smtm.pickle.data.mapper

import com.smtm.pickle.data.source.remote.model.mate.InviteMateResponse
import com.smtm.pickle.domain.model.mate.MateId

fun InviteMateResponse.toDomain(): MateId = MateId(mateId)