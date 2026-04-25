package com.talhakasikci.finn360fe.models

data class DashboardResponse(
    val header: DashboardHeader,
    val marketSummary: ArrayList<Instrument>
)

data class DashboardHeader(
    val greeting: String,
    val userName: String,
    val date: String
)

//data class DashboardMarketSummary(
//    val name: String,
//    val symbol: String,
//    val price: Float,
//    val changePercentage: Float,
//    val iconUrl: String
//)
