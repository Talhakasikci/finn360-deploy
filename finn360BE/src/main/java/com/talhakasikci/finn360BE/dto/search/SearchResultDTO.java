package com.talhakasikci.finn360BE.dto.search;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchResultDTO {
    private String id;
    // Stock Symbol
    private String symbol;       // e.g., AAPL
    // Company or Asset Name
    private String description;  // e.g., Apple Inc.
    // Asset Type
    private String type;         // e.g., Common Stock

    private String iconUrl;
}