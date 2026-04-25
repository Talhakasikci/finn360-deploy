package com.talhakasikci.finn360fe.api

import android.content.Context
import android.content.Intent
import com.talhakasikci.finn360fe.ui.activities.MainActivity
import com.talhakasikci.finn360fe.utils.TokenManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context): Interceptor {
    val tokenManager = TokenManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {

        val token = tokenManager.getToken()

        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        if (token != null) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        val response = chain.proceed(requestBuilder.build())

        if (response.code == 401) {
            // Handle unauthorized error, e.g., clear token or notify user
            tokenManager.clearToken()

            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }
        return response
    }


}