package com.talhakasikci.finn360fe.models.watchlist

import java.math.BigDecimal

data class WatchList(
    val id: String,
    val symbol: String,
    val description: String,
    val assetType: String,
    val currentPrice: Double,
    val priceChangePercent: Double,
    val iconUrl: String?
)