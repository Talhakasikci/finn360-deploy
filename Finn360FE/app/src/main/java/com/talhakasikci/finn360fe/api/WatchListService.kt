package com.talhakasikci.finn360fe.api

import com.talhakasikci.finn360fe.models.watchlist.WatchList
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface WatchListService {

    @GET("api/watchlist")
    suspend fun getWatchList(@Query("sortBy") query: String, @Query("direction") direction: String): Response<ArrayList<WatchList>>

    @POST("api/watchlist")
    suspend fun addToWatchList(symbol: String)

    @DELETE("api/watchlist")
    suspend fun removeFromWatchList(symbol: String)

}