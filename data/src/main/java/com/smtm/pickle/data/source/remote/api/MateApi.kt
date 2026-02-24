package com.smtm.pickle.data.source.remote.api

import com.smtm.pickle.data.source.remote.model.mate.MateRequestBody
import com.smtm.pickle.data.source.remote.model.mate.MateRequestResponse
import com.smtm.pickle.data.source.remote.model.mate.RemoteMate
import com.smtm.pickle.data.source.remote.model.mate.RemoteMateRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MateApi {
    @GET("mates")
    suspend fun getMates(): List<RemoteMate>

    @POST("mates")
    suspend fun requestMate(@Body request: MateRequestBody): MateRequestResponse

    @GET("mates/received")
    suspend fun getReceivedMateRequests(): List<RemoteMateRequest>
}
