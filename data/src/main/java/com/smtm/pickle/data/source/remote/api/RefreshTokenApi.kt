package com.smtm.pickle.data.source.remote.api

import com.smtm.pickle.data.source.remote.model.auth.LoginResponse
import com.smtm.pickle.data.source.remote.model.auth.RefreshTokenRequest
import retrofit2.http.Body
import retrofit2.http.POST

/** Authenticator 내부에서만 사용할 API */
interface RefreshTokenApi {

    /** Authenticator 내부 호출용 API */
    @POST("token")
    suspend fun refreshToken(
        @Body refreshToken: RefreshTokenRequest
    ): LoginResponse

}
