package com.talhakasikci.finn360BE.dto.portfolio;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class PortfolioResponseDTO {
    private String id;
    private String iconUrl;       // e.g. URL to company logo
    private String assetType;
    private String symbol;          // e.g. AAPL
    private String description;     // e.g. Apple Inc.
    private BigDecimal quantity;
    private BigDecimal averagePrice; // Average purchase price
    private BigDecimal currentPrice; // Live price from API
    private BigDecimal totalValue;  // quantity * currentPrice
    private BigDecimal profitLoss;   // (currentPrice - averagePrice) * quantity
    private BigDecimal profitLossPercentage; // ((currentPrice - averagePrice) / averagePrice) * 100
    private BigDecimal changePercentage; // 24h Change
}