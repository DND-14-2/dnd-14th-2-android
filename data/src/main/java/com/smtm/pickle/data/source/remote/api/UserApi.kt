package com.smtm.pickle.data.source.remote.api

import com.smtm.pickle.data.source.remote.model.user.NicknameRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("users/me/nickname")
    suspend fun changeNickname(@Body request: NicknameRequest)

}
