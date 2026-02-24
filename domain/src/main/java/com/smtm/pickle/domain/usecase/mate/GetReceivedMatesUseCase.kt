package com.smtm.pickle.domain.usecase.mate

import com.smtm.pickle.domain.common.utils.runSuspendCatching
import com.smtm.pickle.domain.model.mate.ReceivedMate
import com.smtm.pickle.domain.repository.MateRepository
import javax.inject.Inject

class GetReceivedMatesUseCase @Inject constructor(
    private val mateRepository: MateRepository,
) {
    suspend operator fun invoke(): Result<List<ReceivedMate>> = runSuspendCatching {
        mateRepository.getReceivedMates()
    }
}
