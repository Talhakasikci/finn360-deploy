package com.talhakasikci.finn360fe.repository

import android.content.Context
import com.talhakasikci.finn360fe.api.AuthService
import com.talhakasikci.finn360fe.api.RetrofitClient
import com.talhakasikci.finn360fe.models.Auth.AuthResponse
import com.talhakasikci.finn360fe.models.Auth.LoginRequest
import com.talhakasikci.finn360fe.models.Auth.RegisterRequest
import retrofit2.Response


class AuthRepository(context: Context) {
    private val authService = RetrofitClient.createService(context = context, AuthService::class.java)

    suspend fun loginUser(request: LoginRequest): Response<AuthResponse> {
        return authService.login(request)
    }

    suspend fun registerUser(request: RegisterRequest): Response<AuthResponse> {
        return authService.register(request)
    }
}