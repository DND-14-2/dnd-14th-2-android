package com.smtm.pickle.data.source.remote.api

import com.smtm.pickle.data.source.remote.model.LoginRequest
import com.smtm.pickle.data.source.remote.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("oauth/login")
    suspend fun socialLogin(@Body request: LoginRequest): LoginResponse

}
