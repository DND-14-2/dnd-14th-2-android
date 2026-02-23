package com.smtm.pickle.data.source.remote.api

import com.smtm.pickle.data.source.remote.model.mate.InviteMateRequest
import com.smtm.pickle.data.source.remote.model.mate.RemoteMate
import com.smtm.pickle.domain.model.mate.MateId
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MateApi {

    @POST("mates")
    suspend fun inviteMate(
        @Body request: InviteMateRequest,
    ): MateId

    @GET("mates")
    suspend fun getMates(): List<RemoteMate>
}
