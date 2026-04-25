package com.talhakasikci.finn360fe.api

import com.talhakasikci.finn360fe.models.User
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService{
    @GET("api/user/getUser/{id}")
    suspend fun getUser(@Path("id") id: String): User
}