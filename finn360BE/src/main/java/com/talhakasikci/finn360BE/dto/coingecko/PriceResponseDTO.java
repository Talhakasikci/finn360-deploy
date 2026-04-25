package com.talhakasikci.finn360BE.dto.coingecko;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PriceResponseDTO {

    @JsonProperty("usd")
    private BigDecimal priceInUsd;

    @JsonProperty("usd_24h_change")
    private BigDecimal change24h;
}