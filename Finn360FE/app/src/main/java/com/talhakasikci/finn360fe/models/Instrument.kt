package com.talhakasikci.finn360fe.models

data class Instrument(
    val id: String,
    val name: String,
    val symbol: String,
    val price: Double,
    val changePercentage: Double,
    val iconUrl: String? = null,
    val isFavorite: Boolean = false
)
