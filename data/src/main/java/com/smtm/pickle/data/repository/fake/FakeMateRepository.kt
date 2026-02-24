package com.smtm.pickle.data.repository.fake

import com.smtm.pickle.domain.model.mate.Mate
import com.smtm.pickle.domain.model.mate.MateId
import com.smtm.pickle.domain.model.mate.ReceivedMate
import com.smtm.pickle.domain.repository.MateRepository

class FakeMateRepository : MateRepository {

    override suspend fun inviteMate(invitationCode: String): MateId {
        return MateId(99L)
    }

    override suspend fun getMates(): List<Mate> {
        return listOf(
            Mate(
                id = MateId(1L),
                nickname = "토끼abc",
                invitationCode = "AAAAAA",
                verdictCount = 3,
            ),
            Mate(
                id = MateId(2L),
                nickname = "고양이dd",
                invitationCode = "BBBBBB",
                verdictCount = 0,
            ),
            Mate(
                id = MateId(3L),
                nickname = "펭귄gg",
                invitationCode = "EEEEEE",
                verdictCount = 7,
            ),
        )
    }

    override suspend fun getReceivedMates(): List<ReceivedMate> {
        return listOf(
            ReceivedMate(
                id = MateId(4L),
                nickname = "강아지ee",
                invitationCode = "CCCCCC",
            ),
            ReceivedMate(
                id = MateId(5L),
                nickname = "다람쥐ff",
                invitationCode = "DDDDDD",
            ),
        )
    }
}
