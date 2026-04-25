package com.talhakasikci.finn360fe.repository

import android.content.Context
import com.talhakasikci.finn360fe.api.PortfolioService
import com.talhakasikci.finn360fe.api.RetrofitClient
import com.talhakasikci.finn360fe.models.Portfolio
import com.talhakasikci.finn360fe.models.PortfolioRequest
import com.talhakasikci.finn360fe.models.PortfolioResponse
import okhttp3.ResponseBody
import retrofit2.Response

class PortfolioRepository(context: Context) {

    private val portfolioService = RetrofitClient.createService(context, PortfolioService::class.java)

    suspend fun getUserPortfolio(): Response<PortfolioResponse> {
        return portfolioService.getPortfolioData()
    }

    suspend fun addInvestment(request: PortfolioRequest): Response<ResponseBody> {
        return portfolioService.postPortfolioData(request)
    }

    suspend fun deleteInvestment(symbol: String): Response<ResponseBody> {
        return portfolioService.deletePortfolioData(symbol)
    }
}