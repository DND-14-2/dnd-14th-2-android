package com.smtm.pickle.data.source.remote.api

import com.smtm.pickle.data.source.remote.model.LoginResponse
import retrofit2.http.Header
import retrofit2.http.POST

/** Authenticator 내부에서만 사용할 API */
interface RefreshTokenApi {

    /** Authenticator 내부 호출용 API */
    @POST("auth/refresh")
    suspend fun refreshToken(
        @Header("Authorization") refreshToken: String
    ): LoginResponse

}
