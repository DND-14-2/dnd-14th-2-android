package com.smtm.pickle.domain.usecase.nickname

import com.smtm.pickle.domain.repository.NicknameRepository
import javax.inject.Inject

class GetNicknameUseCase @Inject constructor(
    private val nicknameRepository: NicknameRepository
) {
    suspend operator fun invoke(): String = nicknameRepository.getNickname()
}
