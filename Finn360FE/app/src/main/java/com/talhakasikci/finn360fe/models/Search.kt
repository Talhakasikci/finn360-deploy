package com.talhakasikci.finn360fe.models

data class Search(
    val id: String,
    val symbol: String,
    val description: String,
    val type: String,
    val iconUrl: String?
)