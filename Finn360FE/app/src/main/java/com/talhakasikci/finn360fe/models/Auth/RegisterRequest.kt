package com.talhakasikci.finn360fe.models.Auth

data class RegisterRequest(
    val name: String,
    val surname: String,
    val email: String,
    val password: String
)
