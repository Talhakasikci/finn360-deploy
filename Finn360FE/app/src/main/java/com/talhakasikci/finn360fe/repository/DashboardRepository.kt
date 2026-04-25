package com.talhakasikci.finn360fe.repository

import android.content.Context
import com.talhakasikci.finn360fe.api.DashboardService
import com.talhakasikci.finn360fe.api.RetrofitClient
import com.talhakasikci.finn360fe.models.DashboardResponse
import retrofit2.Response

class DashboardRepository(context: Context) {
    private val dashboardService = RetrofitClient.createService(context = context, DashboardService::class.java)

    suspend fun getDashboard(): Response<DashboardResponse> {
        return dashboardService.getHomeDashboard()
    }
}