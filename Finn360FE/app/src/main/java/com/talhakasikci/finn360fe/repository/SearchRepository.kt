package com.talhakasikci.finn360fe.repository

import android.content.Context
import com.talhakasikci.finn360fe.api.RetrofitClient
import com.talhakasikci.finn360fe.api.SearchService
import com.talhakasikci.finn360fe.models.Search
import retrofit2.Response


class SearchRepository(context: Context) {

    private val searchService = RetrofitClient.createService(context =context, SearchService::class.java )

    suspend fun getSearchResults(request: String): Response<ArrayList<Search>>  {
        return searchService.getSearchInstruments(request)
    }

}