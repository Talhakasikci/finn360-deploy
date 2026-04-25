package com.talhakasikci.finn360BE.dto.dashboard;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class MarketSummaryDTO {
    private String name;
    private String symbol;
    private BigDecimal price;
    private BigDecimal changePercentage;
    private String iconUrl;
}