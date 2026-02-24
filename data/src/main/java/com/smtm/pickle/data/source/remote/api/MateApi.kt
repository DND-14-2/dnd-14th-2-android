package com.smtm.pickle.data.source.remote.api

import com.smtm.pickle.data.source.remote.model.mate.InviteMateRequest
import com.smtm.pickle.data.source.remote.model.mate.MateRequestResponse
import com.smtm.pickle.data.source.remote.model.mate.MateStatusUpdateRequest
import com.smtm.pickle.data.source.remote.model.mate.RemoteMate
import com.smtm.pickle.data.source.remote.model.mate.RemoteMateRequest
import com.smtm.pickle.domain.model.mate.MateId
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface MateApi {

    @POST("mates")
    suspend fun inviteMate(
        @Body request: InviteMateRequest,
    ): MateRequestResponse

    @GET("mates")
    suspend fun getMates(): List<RemoteMate>

    @GET("mates/received")
    suspend fun getReceivedMateRequests(): List<RemoteMateRequest>

    @PATCH("mates/{mateId}")
    suspend fun updateMateStatus(
        @Path("mateId") mateId: Long,
        @Body request: MateStatusUpdateRequest,
    )
}
