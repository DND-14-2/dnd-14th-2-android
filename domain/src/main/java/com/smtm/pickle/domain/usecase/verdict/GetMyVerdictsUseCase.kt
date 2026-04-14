package com.smtm.pickle.domain.usecase.verdict

import com.smtm.pickle.domain.common.utils.runSuspendCatching
import com.smtm.pickle.domain.model.verdict.MyVerdict
import com.smtm.pickle.domain.repository.VerdictRepository
import javax.inject.Inject

class GetMyVerdictsUseCase @Inject constructor(
    private val verdictRepository: VerdictRepository,
) {
    suspend operator fun invoke(): Result<List<MyVerdict>> = runSuspendCatching {
        verdictRepository.getMyVerdicts()
    }
}
