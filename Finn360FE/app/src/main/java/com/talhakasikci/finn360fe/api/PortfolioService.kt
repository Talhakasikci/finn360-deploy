package com.talhakasikci.finn360fe.api

import com.talhakasikci.finn360fe.models.PortfolioRequest
import com.talhakasikci.finn360fe.models.PortfolioResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PortfolioService {

    @GET("api/portfolio")
    suspend fun getPortfolioData(): Response<PortfolioResponse>

    @POST("api/portfolio")
    suspend fun postPortfolioData(@Body request: PortfolioRequest) :Response<ResponseBody>

    @DELETE("api/portfolio")
    suspend fun deletePortfolioData(@Query("symbol") symbol: String): Response<ResponseBody>
}