package com.talhakasikci.finn360fe.api

import com.talhakasikci.finn360fe.models.DashboardResponse
import retrofit2.Response
import retrofit2.http.GET

interface DashboardService {

    @GET("api/dashboard/market-summary")
    suspend fun getHomeDashboard(): Response<DashboardResponse>
}