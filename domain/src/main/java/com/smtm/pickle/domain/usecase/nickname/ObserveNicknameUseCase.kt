package com.smtm.pickle.domain.usecase.nickname

import com.smtm.pickle.domain.repository.NicknameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveNicknameUseCase @Inject constructor(
    private val nicknameRepository: NicknameRepository
) {
    operator fun invoke(): Flow<String> = nicknameRepository.observeNickname()
}
