package com.smtm.pickle.domain.usecase.verdict

import com.smtm.pickle.domain.common.utils.runSuspendCatching
import com.smtm.pickle.domain.model.verdict.JurorVerdict
import com.smtm.pickle.domain.repository.VerdictRepository
import javax.inject.Inject

class GetJurorVerdictsUseCase @Inject constructor(
    private val verdictRepository: VerdictRepository,
) {
    suspend operator fun invoke(): Result<List<JurorVerdict>> = runSuspendCatching {
        verdictRepository.getJurorVerdicts()
    }
}
