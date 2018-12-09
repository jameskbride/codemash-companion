package com.jameskbride.codemashcompanion.network

import com.jameskbride.codemashcompanion.network.model.ApiGroup
import com.jameskbride.codemashcompanion.network.model.ApiSpeaker
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CodemashApi {

    @GET("api/v2/mqm7pgek/view/speakers")
    fun getSpeakers(@Query("type") type: String = "json"): Observable<List<ApiSpeaker>>

    @GET("api/v2/mqm7pgek/view/sessions")
    fun getSessions(@Query("type") type: String = "json"): Observable<List<ApiGroup>>
}