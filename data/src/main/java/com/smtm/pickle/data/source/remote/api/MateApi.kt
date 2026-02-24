package com.smtm.pickle.data.source.remote.api

import com.smtm.pickle.data.source.remote.model.mate.InviteMateRequest
import com.smtm.pickle.data.source.remote.model.mate.InviteMateResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface MateApi {

    @POST("mates")
    suspend fun inviteMate(
        @Body request: InviteMateRequest,
    ): InviteMateResponse
}
