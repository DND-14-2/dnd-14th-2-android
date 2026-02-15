package com.smtm.pickle.data.source.remote.api

import com.smtm.pickle.data.source.remote.model.ledger.LedgerCreateRequest
import com.smtm.pickle.data.source.remote.model.ledger.LedgerEditRequest
import com.smtm.pickle.data.source.remote.model.ledger.LedgersResponse
import com.smtm.pickle.data.source.remote.model.ledger.RemoteLedger
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface LedgerApi {

    @GET("ledgers/summary")
    suspend fun getLedgerSummary(
        @Query("start") from: String,
        @Query("end") to: String
    ): LedgersResponse

    @GET("ledgers/{ledgerId}")
    suspend fun getLedger(
        @Path("ledgerId") id: Long,
    ): RemoteLedger

    @POST("ledgers")
    suspend fun createLedger(
        @Body ledger: LedgerCreateRequest
    ): RemoteLedger

    @PUT("ledgers/{ledgerId}")
    suspend fun editLedger(
        @Path("ledgerId") ledgerId: Long,
        @Body request: LedgerEditRequest
    ): RemoteLedger

    @DELETE("ledgers/{ledgerId}")
    suspend fun deleteLedger(
        @Path("ledgerId") id: Long
    )
}
