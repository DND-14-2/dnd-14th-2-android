package com.smtm.pickle.data.source.remote.api

import com.smtm.pickle.data.source.remote.model.user.NicknameRequest
import com.smtm.pickle.data.source.remote.model.user.NicknameResponse
import com.smtm.pickle.data.source.remote.model.user.RemoteUser
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {

    @GET("users/me")
    suspend fun getProfile(): RemoteUser

    @DELETE("users/me")
    suspend fun withdrawAccount()

    @POST("users/me/nickname")
    suspend fun changeNickname(@Body request: NicknameRequest): NicknameResponse

}

