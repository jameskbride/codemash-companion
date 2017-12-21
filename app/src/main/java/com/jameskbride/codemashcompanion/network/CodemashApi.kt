package com.jameskbride.codemashcompanion.network

import com.jameskbride.codemashcompanion.network.model.ApiSession
import com.jameskbride.codemashcompanion.network.model.ApiSpeaker
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface CodemashApi {

    @GET("api/SpeakersData")
    fun getSpeakers(@Query("type") type: String = "json"): Observable<List<ApiSpeaker>>

    @GET("api/SessionsData")
    fun getSessions(@Query("type") type: String = "json"): Observable<List<ApiSession>>
}