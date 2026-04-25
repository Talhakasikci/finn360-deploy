package com.talhakasikci.finn360BE.dto.watchlist;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WatclistResponseDTO {
    private String id;
    private String instrumentId;
    private String symbol;
    private String description;
    private String assetType;

    private BigDecimal currentPrice;
    private BigDecimal priceChangePercent;
    private String iconUrl;
}
