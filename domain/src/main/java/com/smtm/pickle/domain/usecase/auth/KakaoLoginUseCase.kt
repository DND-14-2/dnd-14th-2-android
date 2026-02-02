package com.smtm.pickle.domain.usecase.auth

import com.smtm.pickle.domain.model.auth.AuthToken
import com.smtm.pickle.domain.model.auth.SocialLoginType
import com.smtm.pickle.domain.repository.AuthRepository
import javax.inject.Inject

class KakaoLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(token: String): Result<AuthToken> =
        authRepository.socialLogin(
            token = token,
            type = SocialLoginType.KAKAO
        )
}
