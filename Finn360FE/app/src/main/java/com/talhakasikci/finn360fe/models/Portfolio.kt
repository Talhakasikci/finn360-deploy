package com.talhakasikci.finn360fe.models


data class PortfolioResponse (
    val totalBalance: Double,
    val totalProfitLoss: Double,
    val totalProfitLossPercentage: Double,
    val items: ArrayList<Portfolio>
)

data class Portfolio (
    val id: String,
    val iconUrl: String?,
    val assetType: String,
    val symbol: String,
    val description: String,
    val quantity: Double,
    val averagePrice: Double,
    val currentPrice: Double,
    val totalValue: Double,
    val profitLoss: Double,
    val profitLossPercentage: Double,
    val changePercentage: Double
)

data class PortfolioRequest(
    val symbol: String,
    val instrumentId: String?, // Kriptolar için gerekli (örn: bitcoin)
    val assetType: String,     // STOCK veya CRYPTO
    val description: String,
    val quantity: Double,
    val averageBuyPrice: Double
)