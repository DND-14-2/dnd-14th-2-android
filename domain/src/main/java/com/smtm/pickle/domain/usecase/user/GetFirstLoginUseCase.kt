package com.smtm.pickle.domain.usecase.user

import com.smtm.pickle.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFirstLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Boolean> = userRepository.isFirstLogin()
}
