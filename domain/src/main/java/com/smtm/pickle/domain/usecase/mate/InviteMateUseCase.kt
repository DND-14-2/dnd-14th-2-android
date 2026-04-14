package com.smtm.pickle.domain.usecase.mate

import com.smtm.pickle.domain.common.utils.runSuspendCatching
import com.smtm.pickle.domain.model.mate.MateId
import com.smtm.pickle.domain.repository.MateRepository
import javax.inject.Inject

class InviteMateUseCase @Inject constructor(
    private val mateRepository: MateRepository,
) {
    suspend operator fun invoke(invitationCode: String): Result<MateId> = runSuspendCatching {
        mateRepository.inviteMate(invitationCode)
    }
}
