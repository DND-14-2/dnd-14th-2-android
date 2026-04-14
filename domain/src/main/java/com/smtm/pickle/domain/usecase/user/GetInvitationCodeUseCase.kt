package com.smtm.pickle.domain.usecase.user

import com.smtm.pickle.domain.common.utils.runSuspendCatching
import com.smtm.pickle.domain.repository.UserRepository
import javax.inject.Inject

class GetInvitationCodeUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(): Result<String> = runSuspendCatching {
        userRepository.getInvitationCode()
    }
}
