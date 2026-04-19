package com.smtm.pickle.data.source.remote.api

import com.smtm.pickle.data.source.remote.model.auth.DemoLoginRequest
import com.smtm.pickle.data.source.remote.model.auth.LoginRequest
import com.smtm.pickle.data.source.remote.model.auth.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("oauth/login")
    suspend fun socialLogin(@Body request: LoginRequest): LoginResponse

    @POST("oauth/demo")
    suspend fun demoLogin(@Body request: DemoLoginRequest): LoginResponse

    @POST("logout")
    suspend fun logout()
}
