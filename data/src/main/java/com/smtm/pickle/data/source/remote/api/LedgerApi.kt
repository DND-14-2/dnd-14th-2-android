package com.smtm.pickle.data.source.remote.api

import com.smtm.pickle.data.source.remote.model.ledger.LedgerCreateRequest
import com.smtm.pickle.data.source.remote.model.ledger.LedgersResponse
import com.smtm.pickle.data.source.remote.model.ledger.RemoteLedger
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LedgerApi {

    @GET("ledgers/summary")
    suspend fun getLedgerSummary(
        @Query("start") from: String,
        @Query("end") to: String
    ): LedgersResponse

    @POST("ledgers")
    suspend fun createLedger(
        @Body ledger: LedgerCreateRequest
    ): RemoteLedger
}
