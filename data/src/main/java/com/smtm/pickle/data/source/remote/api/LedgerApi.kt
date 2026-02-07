package com.smtm.pickle.data.source.remote.api

import com.smtm.pickle.data.source.remote.model.ledger.LedgersResponse
import com.smtm.pickle.data.source.remote.model.ledger.RemoteLedger
import retrofit2.http.GET
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
}
