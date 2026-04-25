package com.talhakasikci.finn360fe.models

import androidx.compose.ui.semantics.Role

data class User(
    val id: String,
    val name: String,
    val surname: String,
    val email: String,
    val role: String
)
