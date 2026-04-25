package com.talhakasikci.finn360fe.api

import com.talhakasikci.finn360fe.models.Auth.AuthResponse
import com.talhakasikci.finn360fe.models.Auth.LoginRequest
import com.talhakasikci.finn360fe.models.Auth.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest) : Response<AuthResponse>

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest) : Response<AuthResponse>
}