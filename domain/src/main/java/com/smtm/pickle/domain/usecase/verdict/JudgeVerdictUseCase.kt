package com.smtm.pickle.domain.usecase.verdict

import com.smtm.pickle.domain.common.utils.runSuspendCatching
import com.smtm.pickle.domain.model.verdict.JurorVerdict
import com.smtm.pickle.domain.model.verdict.VerdictType
import com.smtm.pickle.domain.repository.VerdictRepository
import javax.inject.Inject

class JudgeVerdictUseCase @Inject constructor(
    private val verdictRepository: VerdictRepository,
) {
    suspend operator fun invoke(verdictId: Long, verdictType: VerdictType): Result<JurorVerdict> = runSuspendCatching {
        verdictRepository.judgeVerdict(verdictId, verdictType)
    }
}
