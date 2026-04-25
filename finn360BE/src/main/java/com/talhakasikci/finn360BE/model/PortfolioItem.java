package com.talhakasikci.finn360BE.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;

@Data
@Document(collection = "portfolio")
public class PortfolioItem {

    @Id
    private String id;

    private String instrumentId;
    // User ID (Owner of the asset)
    private String userId;

    private String description;
    // Asset Symbol (e.g., AAPL, BTC)
    private String symbol;

    // Asset Type (STOCK or CRYPTO)
    private String assetType;

    // Quantity owned (e.g., 12.0 pcs)
    private BigDecimal quantity;

    // Average Buy Price (Cost basis)
    private BigDecimal averageBuyPrice;

    private String iconUrl;
    private String currentPrice;
}