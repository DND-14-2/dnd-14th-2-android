package com.smtm.pickle.data.source.remote.api

import com.smtm.pickle.data.source.remote.model.ledger.LedgersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LedgerApi {

    @GET("ledgers/summary")
    suspend fun getLedgerSummary(
        @Query("from") from: String,
        @Query("to") to: String
    ): LedgersResponse
}
