package com.talhakasikci.finn360fe.api

import com.talhakasikci.finn360fe.models.Instrument
import com.talhakasikci.finn360fe.models.Search
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("api/search")
    suspend fun getSearchInstruments(@Query("query") query: String): Response<ArrayList<Search>>
}