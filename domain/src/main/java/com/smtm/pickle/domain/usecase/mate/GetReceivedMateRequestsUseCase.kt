package com.smtm.pickle.domain.usecase.mate

import com.smtm.pickle.domain.common.utils.runSuspendCatching
import com.smtm.pickle.domain.model.mate.MateRequest
import com.smtm.pickle.domain.repository.MateRepository
import javax.inject.Inject

class GetReceivedMateRequestsUseCase @Inject constructor(
    private val mateRepository: MateRepository
) {
    suspend operator fun invoke(): Result<List<MateRequest>> = runSuspendCatching {
        mateRepository.getReceivedMateRequests()
    }
}
