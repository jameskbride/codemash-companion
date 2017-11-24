package com.jameskbride.codemashcompanion.network

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CodemashApi {

    @GET("api/SpeakersData")
    fun getSpeakers(@Query("type") type: String = "json"): Observable<Array<Speaker>>
}