package com.jameskbride.codemashcompanion.network

import com.jameskbride.codemashcompanion.network.model.ApiGrid
import com.jameskbride.codemashcompanion.network.model.ApiRoom
import com.jameskbride.codemashcompanion.network.model.ApiSpeaker
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CodemashApi {

    @GET("api/v2/m4qufajl/view/speakers")
    fun getSpeakers(@Query("type") type: String = "json"): Observable<List<ApiSpeaker>>

    @GET("api/v2/m4qufajl/view/gridTable")
    fun getSessions(@Query("type") type: String = "json"): Observable<List<ApiGrid>>
}