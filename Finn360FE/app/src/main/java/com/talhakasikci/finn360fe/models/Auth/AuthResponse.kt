package com.talhakasikci.finn360fe.models.Auth

data class AuthResponse (
    val token: String,
    val email: String,
    val message: String
)