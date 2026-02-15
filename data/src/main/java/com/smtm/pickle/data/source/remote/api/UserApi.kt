package com.smtm.pickle.data.source.remote.api

import com.smtm.pickle.data.source.remote.model.user.NicknameRequest
import com.smtm.pickle.data.source.remote.model.user.NicknameResponse
import com.smtm.pickle.data.source.remote.model.user.ProfileResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {

    @GET("users/me")
    suspend fun getProfile(): ProfileResponse

    @POST("users/me/nickname")
    suspend fun changeNickname(@Body request: NicknameRequest): NicknameResponse

}

