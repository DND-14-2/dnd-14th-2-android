package com.smtm.pickle.domain.usecase.user

import com.smtm.pickle.domain.common.utils.runSuspendCatching
import com.smtm.pickle.domain.repository.UserRepository
import javax.inject.Inject

class SaveNicknameUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(nickname: String) = runSuspendCatching {
        userRepository.saveNickname(nickname)
    }
}
