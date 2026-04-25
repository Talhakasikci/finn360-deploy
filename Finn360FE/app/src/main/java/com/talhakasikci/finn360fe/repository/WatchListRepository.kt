package com.talhakasikci.finn360fe.repository

import android.content.Context
import com.talhakasikci.finn360fe.api.RetrofitClient
import com.talhakasikci.finn360fe.api.WatchListService
import com.talhakasikci.finn360fe.models.watchlist.WatchList
import retrofit2.Response

class WatchListRepository(context: Context) {
    private val WLservice = RetrofitClient.createService(context, WatchListService::class.java)

    suspend fun getUserWatchList(sortBy: String, direction: String): Response<ArrayList<WatchList>> {
        return WLservice.getWatchList(sortBy, direction)
    }
}