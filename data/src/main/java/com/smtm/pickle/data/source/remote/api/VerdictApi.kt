package com.smtm.pickle.data.source.remote.api

import com.smtm.pickle.data.source.remote.model.verdict.JurorVerdictsResponse
import com.smtm.pickle.data.source.remote.model.verdict.MyVerdictsResponse
import com.smtm.pickle.data.source.remote.model.verdict.RemoteJurorVerdict
import com.smtm.pickle.data.source.remote.model.verdict.VerdictCreateRequest
import com.smtm.pickle.data.source.remote.model.verdict.VerdictJudgeRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface VerdictApi {

    @GET("verdicts/juror")
    suspend fun getJurorVerdicts(): JurorVerdictsResponse

    @GET("verdicts/my")
    suspend fun getMyVerdicts(): MyVerdictsResponse

    @POST("verdicts")
    suspend fun requestVerdict(
        @Body request: VerdictCreateRequest,
    )

    @PATCH("verdicts/{id}")
    suspend fun judgeVerdict(
        @Path("id") id: Long,
        @Body request: VerdictJudgeRequest,
    ): RemoteJurorVerdict
}
